/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.Model;
import fr.paris.lutece.plugins.pluginwizard.business.ModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Application;
import fr.paris.lutece.plugins.pluginwizard.business.model.Attribute;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.Rest;
import fr.paris.lutece.plugins.pluginwizard.web.formbean.BusinessClassFormBean;
import fr.paris.lutece.plugins.pluginwizard.web.formbean.DescriptionFormBean;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Model Service provides all plugin'model manipulations
 */
public final class ModelService
{
    private static AttributeService _serviceAttribute = SpringContextService.getBean( "pluginwizard.attribute.service" );
    private static DozerBeanMapper _mapper = new DozerBeanMapper( );
    private static final String ID = "id";
    private static final String UNDERSCORE = "_";

    /** private constructor */
    private ModelService( )
    {
    }

    /**
     * Create a plugin model
     * 
     * @param strPluginName
     *            The plugin name
     * @return The plugin ID
     */
    public static int createModel( String strPluginName )
    {
        Model model = new Model( );
        model.setName( strPluginName );

        PluginModel pm = new PluginModel( );
        pm.setPluginName( strPluginName );
        model = ModelHome.create( model );
        pm.setIdPlugin( model.getIdPlugin( ) );
        savePluginModel( pm );

        return pm.getIdPlugin( );
    }

    /**
     * Returns the plugin model
     * 
     * @param nPluginId
     *            The plugin's ID
     * @return The plugin model
     */
    public static PluginModel getPluginModel( int nPluginId )
    {
        PluginModel pm;
        Model model = ModelHome.findByPrimaryKey( nPluginId );

        if ( model != null )
        {
            pm = MapperService.readJson( model.getModelJson( ) );
        }
        else
        {
            pm = new PluginModel( );
            pm.setIdPlugin( nPluginId );
        }

        return pm;
    }

    /**
     * Save the plugin model
     * 
     * @param pm
     *            The plugin model
     */
    public static void savePluginModel( PluginModel pm )
    {
        Model model = new Model( );
        model.setIdPlugin( pm.getIdPlugin( ) );
        model.setName( pm.getPluginName( ) );

        String strJson = MapperService.getJson( pm );
        model.setModelJson( strJson );

        if ( ModelHome.findByPrimaryKey( pm.getIdPlugin( ) ) != null )
        {
            ModelHome.update( model );
        }
        else
        {
            ModelHome.create( model );
        }
    }

    /**
     * Save the plugin model from json
     * 
     * @param pm
     *            The plugin model
     * @return
     */
    public static int savePluginModelFromJson( PluginModel pm )
    {
        Model model;
        int nPluginId = ModelHome.exists( pm.getPluginName( ) );

        if ( nPluginId == -1 )
        {
            // if the plugin doesn't exist
            model = new Model( );
            model.setName( pm.getPluginName( ) );

            ModelHome.create( model );
        }
        else
        {
            model = ModelHome.findByPrimaryKey( nPluginId );
        }

        pm.setIdPlugin( model.getIdPlugin( ) );
        String strJson = MapperService.getJson( pm );
        model.setModelJson( strJson );
        ModelHome.update( model );

        return model.getIdPlugin( );
    }

    // //////////////////////////////////////////////////////////////////////////
    // FEATURES
    /**
     * Add a feature
     *
     * @param nPluginId
     *            The plugin's ID
     * @param feature
     *            The feature
     */
    public static void addFeature( int nPluginId, Feature feature )
    {
        PluginModel pm = getPluginModel( nPluginId );
        feature.setId( getMaxFeatureId( pm ) + 1 );
        pm.getFeatures( ).add( feature );
        savePluginModel( pm );
    }

    /**
     * Get a given feature
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nFeatureId
     *            The feature's ID
     * @return The feature
     */
    public static Feature getFeature( int nPluginId, int nFeatureId )
    {
        PluginModel pm = getPluginModel( nPluginId );

        for ( Feature feature : pm.getFeatures( ) )
        {
            if ( feature.getId( ) == nFeatureId )
            {
                return feature;
            }
        }

        return null;
    }

    /**
     * Get The max feature ID
     *
     * @param pm
     *            The Plugin Model
     * @return The max used ID
     */
    private static int getMaxFeatureId( PluginModel pm )
    {
        int nMax = 0;

        for ( Feature feature : pm.getFeatures( ) )
        {
            if ( feature.getId( ) > nMax )
            {
                nMax = feature.getId( );
            }
        }

        return nMax;
    }

    /**
     * Update a feature
     *
     * @param nPluginId
     *            The plugin's ID
     * @param feature
     *            The feature
     */
    public static void updateFeature( int nPluginId, Feature feature )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<Feature> list = pm.getFeatures( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            Feature f = list.get( i );

            if ( f.getId( ) == feature.getId( ) )
            {
                list.set( i, feature );
                savePluginModel( pm );

                break;
            }
        }
    }

    /**
     * Remove a feature
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nFeatureId
     *            The feature's ID
     */
    public static void removeFeature( int nPluginId, int nFeatureId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<Feature> list = pm.getFeatures( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            Feature f = list.get( i );

            if ( f.getId( ) == nFeatureId )
            {
                list.remove( i );
                savePluginModel( pm );

                break;
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    // APPLICATIONS

    /**
     * Get a given application
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nApplicationId
     *            The application ID
     * @return The application
     */
    public static Application getApplication( int nPluginId, int nApplicationId )
    {
        PluginModel pm = getPluginModel( nPluginId );

        return getApplication( pm, nApplicationId );
    }

    /**
     * Get a given application
     *
     * @param pm
     *            The plugin model
     * @param nApplicationId
     *            The application ID
     * @return The application
     */
    public static Application getApplication( PluginModel pm, int nApplicationId )
    {
        for ( Application application : pm.getApplications( ) )
        {
            if ( application.getId( ) == nApplicationId )
            {
                return application;
            }
        }

        return null;
    }

    /**
     * Add an application to the model
     *
     * @param nPluginId
     *            The plugin's ID
     * @param application
     *            The application
     */
    public static void addApplication( int nPluginId, Application application )
    {
        PluginModel pm = getPluginModel( nPluginId );
        application.setId( getMaxApplicationId( pm ) + 1 );
        pm.getApplications( ).add( application );
        savePluginModel( pm );
    }

    /**
     * Get The max application ID
     *
     * @param pm
     *            The Plugin Model
     * @return The max used ID
     */
    private static int getMaxApplicationId( PluginModel pm )
    {
        int nMax = 0;

        for ( Application application : pm.getApplications( ) )
        {
            if ( application.getId( ) > nMax )
            {
                nMax = application.getId( );
            }
        }

        return nMax;
    }

    /**
     * Update an application
     *
     * @param nPluginId
     *            The plugin's ID
     * @param application
     *            The application
     */
    public static void updateApplication( int nPluginId, Application application )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<Application> list = pm.getApplications( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            Application app = list.get( i );

            if ( app.getId( ) == application.getId( ) )
            {
                list.set( i, application );
                savePluginModel( pm );

                break;
            }
        }
    }

    /**
     * Remove an application
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nApplicationId
     *            The application's ID
     */
    public static void removeApplication( int nPluginId, int nApplicationId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<Application> list = pm.getApplications( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            Application f = list.get( i );

            if ( f.getId( ) == nApplicationId )
            {
                list.remove( i );
                savePluginModel( pm );

                break;
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    // PORTLET

    /**
     * Get a given portlet
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nPortletId
     *            The portlet ID
     * @return The portlet
     */
    public static Portlet getPortlet( int nPluginId, int nPortletId )
    {
        PluginModel pm = getPluginModel( nPluginId );

        for ( Portlet portlet : pm.getPortlets( ) )
        {
            if ( portlet.getId( ) == nPortletId )
            {
                return portlet;
            }
        }

        return null;
    }

    /**
     * Add an portlet to the model
     *
     * @param nPluginId
     *            The plugin's ID
     * @param portlet
     *            The portlet
     */
    public static void addPortlet( int nPluginId, Portlet portlet )
    {
        PluginModel pm = getPluginModel( nPluginId );
        portlet.setId( getMaxPortletId( pm ) + 1 );
        pm.getPortlets( ).add( portlet );
        savePluginModel( pm );
    }

    /**
     * Get The max portlet ID
     *
     * @param pm
     *            The Plugin Model
     * @return The max used ID
     */
    private static int getMaxPortletId( PluginModel pm )
    {
        int nMax = 0;

        for ( Portlet portlet : pm.getPortlets( ) )
        {
            if ( portlet.getId( ) > nMax )
            {
                nMax = portlet.getId( );
            }
        }

        return nMax;
    }

    /**
     * Update an portlet
     *
     * @param nPluginId
     *            The plugin's ID
     * @param portlet
     *            The portlet
     */
    public static void updatePortlet( int nPluginId, Portlet portlet )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<Portlet> list = pm.getPortlets( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            Portlet p = list.get( i );

            if ( p.getId( ) == portlet.getId( ) )
            {
                list.set( i, portlet );
                savePluginModel( pm );

                break;
            }
        }
    }

    /**
     * Remove an portlet
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nPortletId
     *            The portlet's ID
     */
    public static void removePortlet( int nPluginId, int nPortletId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<Portlet> list = pm.getPortlets( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            Portlet p = list.get( i );

            if ( p.getId( ) == nPortletId )
            {
                list.remove( i );
                savePluginModel( pm );

                break;
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    // BUSINESS CLASSES
    /**
     * Get a given business class
     * 
     * @param nPluginId
     *            The plugin's ID
     * @param nBusinessClassId
     *            The business class ID
     * @return The business class
     */
    public static BusinessClass getBusinessClass( int nPluginId, int nBusinessClassId )
    {
        PluginModel pm = getPluginModel( nPluginId );

        return getBusinessClass( pm, nBusinessClassId );
    }

    /**
     * Get a given business class
     * 
     * @param pm
     *            The plugin model
     * @param nBusinessClassId
     *            The business class ID
     * @return The business class
     */
    public static BusinessClass getBusinessClass( PluginModel pm, int nBusinessClassId )
    {
        for ( BusinessClass bc : pm.getBusinessClasses( ) )
        {
            if ( bc.getId( ) == nBusinessClassId )
            {
                return bc;
            }
        }

        return null;
    }

    /**
     * Add an bc to the model
     *
     * @param nPluginId
     *            The plugin's ID
     * @param bc
     *            The business class
     * @return The business class with its ID
     */
    public static BusinessClass addBusinessClass( int nPluginId, BusinessClassFormBean bc )
    {
        PluginModel pm = getPluginModel( nPluginId );
        BusinessClass businessClass = _mapper.map( bc, BusinessClass.class );
        businessClass.setId( getMaxBusinessClassId( pm ) + 1 );

        String strBusinessClass = "";
        char charBusinessClass [ ] = bc.getBusinessClass( ).toCharArray( );
        for ( int i = 0; i < charBusinessClass.length; i++ )
        {
            if ( Character.isUpperCase( charBusinessClass [i] ) )
            {
                strBusinessClass += UNDERSCORE;
            }
            strBusinessClass += Character.toLowerCase( charBusinessClass [i] );
        }
        businessClass.setPrimaryKey( ID + strBusinessClass );

        pm.getBusinessClasses( ).add( businessClass );
        savePluginModel( pm );

        return businessClass;
    }

    /**
     * Get The max bc ID
     *
     * @param pm
     *            The Plugin Model
     * @return The max used ID
     */
    private static int getMaxBusinessClassId( PluginModel pm )
    {
        int nMax = 0;

        for ( BusinessClass bc : pm.getBusinessClasses( ) )
        {
            if ( bc.getId( ) > nMax )
            {
                nMax = bc.getId( );
            }
        }

        return nMax;
    }

    /**
     * Update an businessClass
     *
     * @param nPluginId
     *            The plugin's ID
     * @param businessClass
     *            The businessClass
     */
    public static void updateBusinessClass( int nPluginId, BusinessClassFormBean businessClass )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<BusinessClass> list = pm.getBusinessClasses( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            BusinessClass bc = list.get( i );

            if ( bc.getId( ) == businessClass.getId( ) )
            {
                _mapper.map( businessClass, bc );
                savePluginModel( pm );

                break;
            }
        }
    }

    /**
     * Remove a business class
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nBusinessClassId
     *            The business class's ID
     */
    public static void removeBusinessClass( int nPluginId, int nBusinessClassId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        List<BusinessClass> list = pm.getBusinessClasses( );

        for ( int i = 0; i < list.size( ); i++ )
        {
            BusinessClass bc = list.get( i );

            if ( bc.getId( ) == nBusinessClassId )
            {
                list.remove( i );
                savePluginModel( pm );

                break;
            }
        }
    }

    /**
     * Remove all data for a given plugin
     * 
     * @param nPluginId
     *            The plugin ID
     */
    public static void removeAll( int nPluginId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        pm.getApplications( ).clear( );
        pm.getBusinessClasses( ).clear( );
        pm.getFeatures( ).clear( );
        pm.getPortlets( ).clear( );
        savePluginModel( pm );
    }

    // //////////////////////////////////////////////////////////////////////////
    // BUSINESS CLASS ATTRIBUTES
    /**
     * Get a given attribute
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nBusinessClassId
     *            The business class ID
     * @param nAttributeId
     *            The attribute ID
     * @return The attribute
     */
    public static Attribute getAttribute( int nPluginId, int nBusinessClassId, int nAttributeId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        BusinessClass bc = getBusinessClass( pm, nBusinessClassId );
        if ( bc != null )
        {
            List<Attribute> listAttributes = bc.getAttributes( );

            for ( Attribute attribute : listAttributes )
            {
                if ( attribute.getId( ) == nAttributeId )
                {
                    return attribute;
                }
            }

            return null;
        }
        return null;
    }

    /**
     * Add an attribute to a business class
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nBusinessClassId
     *            The business class ID
     * @param attribute
     *            The Attribute
     */
    public static void addAttribute( int nPluginId, int nBusinessClassId, Attribute attribute )
    {
        PluginModel pm = getPluginModel( nPluginId );
        BusinessClass bc = getBusinessClass( pm, nBusinessClassId );
        if ( bc != null )
        {
            List<Attribute> listAttributes = bc.getAttributes( );
            attribute.setId( getMaxAttributeId( listAttributes ) + 1 );
            attribute.setMaxLength( getAttributeMaxLength( attribute.getAttributeTypeId( ) ) );

            listAttributes.add( attribute );
            savePluginModel( pm );
        }
    }

    /**
     * Get The max attribute ID from a list
     *
     * @param listAttributes
     *            The attribute list
     * @return The max used ID
     */
    private static int getMaxAttributeId( List<Attribute> listAttributes )
    {
        int nMax = 0;

        for ( Attribute attribute : listAttributes )
        {
            if ( attribute.getId( ) > nMax )
            {
                nMax = attribute.getId( );
            }
        }

        return nMax;
    }

    /**
     * Update an attribute
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nBusinessClassId
     *            The business class ID
     * @param attribute
     *            The attribute
     */
    public static void updateAttribute( int nPluginId, int nBusinessClassId, Attribute attribute )
    {
        PluginModel pm = getPluginModel( nPluginId );
        BusinessClass bc = getBusinessClass( pm, nBusinessClassId );
        if ( bc != null )
        {
            List<Attribute> list = bc.getAttributes( );

            for ( int i = 0; i < list.size( ); i++ )
            {
                Attribute attr = list.get( i );

                if ( attr.getId( ) == attribute.getId( ) )
                {
                    list.set( i, attribute );

                    savePluginModel( pm );

                    break;
                }
            }
        }
    }

    /**
     * Remove an attribute
     *
     * @param nPluginId
     *            The plugin's ID
     * @param nBusinessClassId
     *            The business class ID
     * @param nAttributeId
     *            The attribute's ID
     */
    public static void removeAttribute( int nPluginId, int nBusinessClassId, int nAttributeId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        BusinessClass bc = getBusinessClass( pm, nBusinessClassId );
        List<Attribute> list;
        if ( bc != null )
        {
            list = bc.getAttributes( );
            for ( int i = 0; i < list.size( ); i++ )
            {
                Attribute attr = list.get( i );

                if ( attr.getId( ) == nAttributeId )
                {
                    list.remove( i );
                    savePluginModel( pm );

                    break;
                }
            }
        }

    }

    // //////////////////////////////////////////////////////////////////////////
    // REST
    /**
     * Add the rest
     *
     * @param nPluginId
     *            The plugin's ID
     * @param rest
     *            The rest
     */
    public static void addRest( int nPluginId, Rest rest )
    {
        PluginModel pm = getPluginModel( nPluginId );
        pm.setRest( rest );
        savePluginModel( pm );
    }

    /**
     * Get the rest
     *
     * @param nPluginId
     *            The plugin's ID
     * @return The rest
     */
    public static Rest getRest( int nPluginId )
    {
        PluginModel pm = getPluginModel( nPluginId );
        return pm.getRest( );
    }

    /**
     * Update the rest
     *
     * @param nPluginId
     *            The plugin's ID
     * @param rest
     *            The rest
     */
    public static void updateRest( int nPluginId, Rest rest )
    {
        PluginModel pm = getPluginModel( nPluginId );
        pm.setRest( rest );
        savePluginModel( pm );
    }

    /**
     * Gets all business classes for a given Application
     * 
     * @param pm
     *            The plugin model
     * @return The list of business class
     */
    public static List<BusinessClass> getBusinessClassesByRest( PluginModel pm )
    {
        List<BusinessClass> list = new ArrayList<>( );
        List<BusinessClass> listAll = pm.getBusinessClasses( );
        Rest rest = pm.getRest( );

        for ( int i : rest.getIdBusinessClasses( ) )
        {
            for ( BusinessClass bc : listAll )
            {
                if ( bc.getId( ) == i )
                {
                    list.add( bc );
                }
            }
        }
        return list;
    }

    /**
     * Gets all business classes for a given Application
     * 
     * @param pm
     *            The plugin model
     * @param nApplicationId
     *            The Application's ID
     * @return The list of business class
     */
    public static List<BusinessClass> getBusinessClassesByApplication( PluginModel pm, int nApplicationId )
    {
        List<BusinessClass> list = new ArrayList<>( );
        List<Application> listAll = pm.getApplications( );
        List<BusinessClass> listAll2 = pm.getBusinessClasses( );

        for ( Application a : listAll )
        {
            if ( a.getId( ) == nApplicationId )
            {
                for ( int i : a.getIdBusinessClasses( ) )
                {
                    for ( BusinessClass bc : listAll2 )
                    {
                        if ( bc.getId( ) == i )
                        {
                            list.add( bc );
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * Gets all business classes for a given Feature
     * 
     * @param pm
     *            The plugin model
     * @param nFeatureId
     *            The feature's ID
     * @return The list of business class
     */
    public static List<BusinessClass> getBusinessClassesByFeature( PluginModel pm, int nFeatureId )
    {
        List<BusinessClass> list = new ArrayList<>( );
        List<Feature> listAll = pm.getFeatures( );
        List<BusinessClass> listAll2 = pm.getBusinessClasses( );

        for ( Feature f : listAll )
        {
            if ( f.getId( ) == nFeatureId )
            {
                for ( int i : f.getIdBusinessClasses( ) )
                {
                    for ( BusinessClass bc : listAll2 )
                    {
                        if ( bc.getId( ) == i )
                        {
                            list.add( bc );
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * Gets the feature for a given Business Class
     * 
     * @param pm
     *            The plugin model
     * @param nBusinessClassId
     *            The Business Class's ID
     * @return The feature
     */
    public static Feature getFeatureByBusinessClass( PluginModel pm, int nBusinessClassId )
    {
        Feature feature = new Feature( );
        List<Feature> listAll = pm.getFeatures( );
        List<BusinessClass> listAll2 = pm.getBusinessClasses( );

        for ( Feature f : listAll )
        {
            for ( int i : f.getIdBusinessClasses( ) )
            {
                if ( i == nBusinessClassId )
                {
                    for ( BusinessClass bc : listAll2 )
                    {
                        if ( bc.getId( ) == i )
                        {
                            feature = f;
                        }
                    }
                }
            }
        }

        return feature;
    }

    /**
     * Gets the application for a given Business Class
     * 
     * @param pm
     *            The plugin model
     * @param nBusinessClassId
     *            The Business Class's ID
     * @return The application
     */
    public static Application getApplicationByBusinessClass( PluginModel pm, int nBusinessClassId )
    {
        Application application = new Application( );
        List<Application> listAll = pm.getApplications( );
        List<BusinessClass> listAll2 = pm.getBusinessClasses( );

        for ( Application a : listAll )
        {
            for ( int i : a.getIdBusinessClasses( ) )
            {
                if ( i == nBusinessClassId )
                {
                    for ( BusinessClass bc : listAll2 )
                    {
                        if ( bc.getId( ) == i )
                        {
                            application = a;
                        }
                    }
                }
            }
        }

        return application;
    }

    /**
     * Returns a Reference list with all Business Classes * @param nPluginId The Plugin's ID
     * 
     * @param nPluginId
     * @return The list
     */
    public static ReferenceList getComboBusinessClasses( int nPluginId )
    {
        ReferenceList list = new ReferenceList( );

        for ( BusinessClass bc : getPluginModel( nPluginId ).getBusinessClasses( ) )
        {
            list.addItem( bc.getId( ), bc.getBusinessClass( ) );
        }

        return list;
    }

    /**
     * Gets all attribute types
     * 
     * @return A list of attributes types
     */
    public static ReferenceList getAttributeTypes( )
    {
        return _serviceAttribute.getAttributeTypes( );
    }

    /**
     * Returns the attribute type corresponding to an ID
     * 
     * @param nAttributeTypeId
     *            The attribute type ID
     * @return The type
     */
    public static String getAttributeType( int nAttributeTypeId )
    {
        AttributeType type = _serviceAttribute.getType( nAttributeTypeId );

        return type.getJavaType( );
    }

    /**
     * Return the attribute type max length
     * 
     * @param nAttributeTypeId
     *            The attribute type ID
     * @return The max length
     */
    private static int getAttributeMaxLength( int nAttributeTypeId )
    {
        AttributeType type = _serviceAttribute.getType( nAttributeTypeId );

        return type.getMaxLength( );
    }

    /**
     * Returns the attribute prefix corresponding to an ID
     * 
     * @param nAttributeTypeId
     *            The attribute type ID
     * @return The type
     */
    public static String getAttributePrefix( int nAttributeTypeId )
    {
        return _serviceAttribute.getType( nAttributeTypeId ).getPrefix( );
    }

    /**
     * Returns the attribute constraint corresponding to an ID
     * 
     * @param nAttributeTypeId
     *            The attribute type ID
     * @return The type
     */
    public static String getAttributeConstraint( int nAttributeTypeId )
    {
        return _serviceAttribute.getType( nAttributeTypeId ).getConstraint( );
    }

    /**
     * Returns the attribute type description
     * 
     * @param nAttributeTypeId
     *            The attribute type ID
     * @return The type description
     */
    public static String getAttributeTypeDescription( int nAttributeTypeId )
    {
        return _serviceAttribute.getType( nAttributeTypeId ).getDescription( );
    }

    public static void updateDescription( int nPluginId, DescriptionFormBean description )
    {
        PluginModel pm = getPluginModel( nPluginId );
        _mapper.map( description, pm );
        savePluginModel( pm );
    }

    public static DescriptionFormBean getDescription( int nPluginId )
    {
        PluginModel pm = getPluginModel( nPluginId );

        return _mapper.map( pm, DescriptionFormBean.class );
    }

    public static BusinessClassFormBean getFormBusinessClass( int nPluginId, int nBusinessClassId )
    {
        PluginModel pm = getPluginModel( nPluginId );

        return _mapper.map( getBusinessClass( pm, nBusinessClassId ), BusinessClassFormBean.class );
    }
}
