/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.pluginwizard.business.model;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.DAOUtil;
import fr.paris.lutece.util.sql.Transaction;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides Data Access methods for BusinessClass objects
 */
public final class BusinessClassDAO implements IBusinessClassDAO
{
    // Constants

    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_business_class ) FROM pluginwizard_plugin_business";
    private static final String SQL_QUERY_SELECT = "select id_business_class,business_class, business_table_name, id_plugin_feature FROM pluginwizard_plugin_business WHERE id_business_class=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_business ( id_business_class, business_class, business_table_name, id_plugin_feature ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_business WHERE id_business_class = ";
    private static final String SQL_QUERY_DELETE_ATTRIBUTES = "DELETE FROM pluginwizard_plugin_attribute WHERE id_business_class = ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_business SET id_business_class = ?, business_class = ?, business_table_name = ? WHERE id_business_class = ?";
    private static final String SQL_QUERY_SELECTALL_BY_PLUGIN = "SELECT a.id_plugin_feature,a.id_business_class, a.business_class, a.business_table_name FROM pluginwizard_plugin_business as a ,"
            + " pluginwizard_plugin_feature as b WHERE a.id_plugin_feature = b.id_plugin_feature AND b.id_plugin= ?";
    private static final String SQL_QUERY_SELECTALL_BY_FEATURE = "SELECT id_plugin_feature, id_business_class, business_class, business_table_name  FROM pluginwizard_plugin_business "
            + " WHERE id_plugin_feature = ?";
    private static final String SQL_QUERY_SELECT_ATTRIBUTES_BY_BUSINESS_CLASS_ID = "select a.id_attribute,a.attribute_type_id,a.attribute_name,a.is_primary_key,a.is_description, b.attribute_type_name FROM pluginwizard_plugin_attribute as a , pluginwizard_plugin_attribute_type as b WHERE a.attribute_type_id=b.attribute_type_id AND a.id_business_class = ? ";
    private static final String SQL_QUERY_COUNT_FIND_BY_FEATURE_AND_CLASS = "SELECT COUNT(id_business_class) FROM pluginwizard_plugin_business WHERE id_plugin_feature= ? AND business_class = ?";
    private static final String SQL_QUERY_CHECK_ATTRIBUTES = "SELECT id_business_class FROM pluginwizard_plugin_attribute WHERE id_business_class= ?";
    private static final String SQL_QUERY_CHECK_BUSINESS_PRIMARY_KEY = "SELECT count( id_attribute ) FROM pluginwizard_plugin_attribute WHERE id_business_class = ? AND is_primary_key= 1";
    private static final String SQL_QUERY_CHECK_BUSINESS_DESCRIPTION = "SELECT count( id_attribute ) FROM pluginwizard_plugin_attribute WHERE id_business_class = ? AND is_description= 1";

    /**
     * Generates a new primary key
     *
     * @param plugin The Plugin
     * @return The new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery();

        int nKey;

        daoUtil.next();
        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free();

        return nKey;
    }

    /**
     * Insert a new record in the table.
     *
     * @param businessClass instance of the BusinessClass object to insert
     * @param plugin The plugin
     */
    @Override
    public void insert( BusinessClass businessClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        businessClass.setIdBusinessClass( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, businessClass.getIdBusinessClass() );
        daoUtil.setString( 2, businessClass.getBusinessClass() );
        daoUtil.setString( 3, businessClass.getBusinessTableName() );
        daoUtil.setInt( 4, businessClass.getIdFeature() );

        daoUtil.executeUpdate();
        daoUtil.free();
    }

    /**
     * Load the data of the businessClass from the table
     *
     * @param nId The identifier of the businessClass
     * @param plugin The plugin
     * @return the instance of the BusinessClass
     */
    @Override
    public BusinessClass load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery();

        BusinessClass businessClass = null;

        if (daoUtil.next())
        {
            businessClass = new BusinessClass();

            businessClass.setIdBusinessClass( daoUtil.getInt( 1 ) );
            businessClass.setBusinessClass( daoUtil.getString( 2 ) );
            businessClass.setBusinessTableName( daoUtil.getString( 3 ) );
            businessClass.setIdFeature( daoUtil.getInt( 4 ) );

            Collection<Attribute> listAttributes = selectAttributeList( nId, plugin );
            businessClass.setAttributes( listAttributes );
        }

        daoUtil.free();

        return businessClass;
    }

    /**
     * Delete a record from the table
     *
     * @param nBusinessClassId The identifier of the businessClass
     * @param plugin The plugin
     */
    @Override
    public void delete( int nBusinessClassId, Plugin plugin )
    {
        Transaction t = new Transaction( plugin );
        try
        {
            t.prepareStatement( SQL_QUERY_DELETE_ATTRIBUTES + nBusinessClassId );
            t.executeStatement();
            t.prepareStatement( SQL_QUERY_DELETE + nBusinessClassId );
            t.executeStatement();
            t.commit();
        }
        catch (SQLException ex)
        {
            AppLogService.error( ex.getMessage(), ex );
            t.rollback();

        }
    }

    /**
     * Updates the record in the table
     *
     * @param businessClass The reference of the businessClass
     * @param plugin The plugin
     */
    @Override
    public void store( BusinessClass businessClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, businessClass.getIdBusinessClass() );
        daoUtil.setString( 2, businessClass.getBusinessClass() );
        daoUtil.setString( 3, businessClass.getBusinessTableName() );

        daoUtil.setInt( 4, businessClass.getIdBusinessClass() );

        daoUtil.executeUpdate();
        daoUtil.free();
    }

    /**
     * Returns a list of business classe
     *
     * @param nPluginId The plugin id
     * @param plugin The plugin
     * @return A collection of business classes
     */
    @Override
    public Collection<BusinessClass> selectBusinessClassesList( int nPluginId, Plugin plugin )
    {
        Collection<BusinessClass> businessClassList = new ArrayList<BusinessClass>();

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeQuery();

        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );

        while (daoUtil.next())
        {
            BusinessClass businessClass = new BusinessClass();
            businessClass.setIdFeature( daoUtil.getInt( 1 ) );

            int nIdBusinessClass = daoUtil.getInt( 2 );

            businessClass.setIdBusinessClass( nIdBusinessClass );
            businessClass.setBusinessClass( daoUtil.getString( 3 ) );
            businessClass.setBusinessTableName( daoUtil.getString( 4 ) );
            businessClass.setPackageName( pluginModel.getPluginName() );
            businessClass.setPluginName( pluginModel.getPluginName() );

            Collection<Attribute> listAttributes = selectAttributeList( nIdBusinessClass, plugin );
            businessClass.setAttributes( listAttributes );

            //Add the primary key and the description
            String strPrimaryAttributeName = AttributeHome.findPrimaryKey( nIdBusinessClass, plugin );
            String strDescriptionAttributeName = AttributeHome.findClassDescription( nIdBusinessClass, plugin );
            businessClass.setPrimaryKey( strPrimaryAttributeName );
            businessClass.setClassDescription( strDescriptionAttributeName );
            businessClassList.add( businessClass );
        }

        daoUtil.free();

        return businessClassList;
    }

    /**
     * Returns a list of Attribute
     *
     * @param nBusinessClassId The business class id
     * @param plugin The plugin
     * @return A collection of attributes
     */
    private Collection<Attribute> selectAttributeList( int nBusinessClassId, Plugin plugin )
    {
        Collection<Attribute> listAttributes = new ArrayList<Attribute>();

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ATTRIBUTES_BY_BUSINESS_CLASS_ID, plugin );
        daoUtil.setInt( 1, nBusinessClassId );
        daoUtil.executeQuery();

        while (daoUtil.next())
        {
            Attribute attribute = new Attribute();
            attribute.setIdAttribute( daoUtil.getInt( 1 ) );
            attribute.setAttributeTypeId( daoUtil.getInt( 2 ) );
            attribute.setAttributeName( daoUtil.getString( 3 ) );
            attribute.setIsPrimary( daoUtil.getBoolean( 4 ) );
            attribute.setIsDescription( daoUtil.getBoolean( 5 ) );
            attribute.setType( daoUtil.getString( 6 ) );
            listAttributes.add( attribute );
        }

        daoUtil.free();

        return listAttributes;
    }

    /**
     * Returns a collection of business classes
     *
     * @param nFeatureId The id of the feature
     * @param nPluginId The id of the plugin
     * @param plugin The Plugin
     * @return A collection of business classes selected by feature
     */
    @Override
    public Collection<BusinessClass> selectBusinessClassesByFeature( int nFeatureId, Plugin plugin )
    {
        Collection<BusinessClass> businessClassList = new ArrayList<BusinessClass>();

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_FEATURE , plugin );
        daoUtil.setInt( 1, nFeatureId );
        daoUtil.executeQuery();

        Feature feature = FeatureHome.findByPrimaryKey( nFeatureId, plugin );
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( feature.getIdPlugin(), plugin );

        while (daoUtil.next())
        {
            BusinessClass businessClass = new BusinessClass();
            businessClass.setIdFeature( daoUtil.getInt( 1 ) );

            int nIdBusinessClass = daoUtil.getInt( 2 );

            businessClass.setIdBusinessClass( nIdBusinessClass );
            businessClass.setBusinessClass( daoUtil.getString( 3 ) );
            businessClass.setBusinessTableName( daoUtil.getString( 4 ) );
            businessClass.setPackageName( pluginModel.getPluginName() );
            businessClass.setPluginName( pluginModel.getPluginName() );

            Collection<Attribute> listAttributes = selectAttributeList( nIdBusinessClass, plugin );
            businessClass.setAttributes( listAttributes );

            //Add the primary key and the description
            String strPrimaryAttributeName = AttributeHome.findPrimaryKey( nIdBusinessClass, plugin );
            String strDescriptionAttributeName = AttributeHome.findClassDescription( nIdBusinessClass, plugin );
            businessClass.setPrimaryKey( strPrimaryAttributeName );
            businessClass.setClassDescription( strDescriptionAttributeName );
            businessClassList.add( businessClass );
        }

        daoUtil.free();

        return businessClassList;
    }

    /**
     * Verifies whether key exists
     *
     * @param nFeatureId the id of the feature
     * @param strBusinessClass The business class name
     * @param plugin The plugin
     * @return A boolean value which is true when class has attributes
     */
    @Override
    public boolean keyExists( int nFeatureId, String strBusinessClass, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_COUNT_FIND_BY_FEATURE_AND_CLASS, plugin );
        daoUtil.setInt( 1, nFeatureId );
        daoUtil.setString( 2, strBusinessClass );
        daoUtil.executeQuery();

        if (daoUtil.next())
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free();

        if (nCount > 0)
        {
            bValue = true;
        }

        return bValue;
    }

    /**
     * Verifies whether business class has attributes
     *
     * @param nBusinessClassId the id of the business class
     * @param plugin The plugin
     * @return A boolean value which is true when class has attributes
     */
    @Override
    public boolean containsAttributes( int nBusinessClassId, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_ATTRIBUTES, plugin );
        daoUtil.setInt( 1, nBusinessClassId );
        daoUtil.executeQuery();

        if (daoUtil.next())
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free();

        if (nCount > 0)
        {
            bValue = true;
        }

        return bValue;
    }

    /**
     * Verifies whether business class has a description
     *
     * @param nIdBusinessClass the id of the business class
     * @param plugin The plugin
     * @return A boolean value which is true when class has a description
     */
    @Override
    public boolean hasAlreadyDescription( int nIdBusinessClass, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_BUSINESS_DESCRIPTION, plugin );
        daoUtil.setInt( 1, nIdBusinessClass );
        daoUtil.executeQuery();

        if (daoUtil.next())
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free();

        if (nCount > 0)
        {
            bValue = true;
        }

        return bValue;
    }

    /**
     * Verifies whether business class has a description
     *
     * @param nIdBusinessClass the id of the business class
     * @param plugin The plugin
     * @return A boolean value which is true when class has a key
     */
    @Override
    public boolean hasAlreadyKey( int nIdBusinessClass, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_BUSINESS_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nIdBusinessClass );
        daoUtil.executeQuery();

        if (daoUtil.next())
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free();

        if (nCount > 0)
        {
            bValue = true;
        }

        return bValue;
    }
}
