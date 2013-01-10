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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for BusinessClass objects
 */
public final class BusinessClassDAO implements IBusinessClassDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_business_class ) FROM pluginwizard_plugin_business";
    private static final String SQL_QUERY_SELECT = "select id_business_class,business_class, business_table_name FROM pluginwizard_plugin_business WHERE id_business_class=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_business ( id_business_class, business_class, business_table_name) VALUES ( ?,?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_business WHERE id_business_class = ? ";
    private static final String SQL_QUERY_DELETE_BY_FEATURE = "DELETE FROM pluginwizard_plugin_business WHERE id_feature = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_business SET id_business_class = ?, business_class = ?, business_table_name = ? WHERE id_business_class = ?";
    private static final String SQL_QUERY_SELECTALL_BY_PLUGIN = "SELECT b.id_plugin_feature,a.id_business_class, a.business_class, a.business_table_name FROM pluginwizard_plugin_business as a ," +
        " pluginwizard_plugin_feature_business as b , pluginwizard_plugin_id_feature as c where b.id_plugin_feature = c.id_plugin_feature " +
        "AND b.id_business_class=a.id_business_class AND c.id_plugin= ?";
    private static final String SQL_QUERY_SELECT_ATTRIBUTES_BY_BUSINESS_CLASS_ID = "select d.id_attribute,d.attribute_type_id,d.attribute_name,d.is_primary_key,d.is_description, e.attribute_type_name FROM pluginwizard_plugin_feature_business as b ,pluginwizard_plugin_business_attribute as c,pluginwizard_plugin_attribute as d, pluginwizard_plugin_attribute_type as e WHERE b.id_business_class = ? AND b.id_business_class=c.id_business_class AND c.id_attribute=d.id_attribute AND d.attribute_type_id=e.attribute_type_id";
    private static final String SQL_QUERY_COUNT_FIND_BY_FEATURE_AND_CLASS = "SELECT COUNT(a.id_business_class) FROM pluginwizard_plugin_business as a ,pluginwizard_plugin_feature_business as b WHERE a.business_class= ? AND b.id_plugin_feature= ? AND b.id_business_class=a.id_business_class";
    private static final String SQL_QUERY_DELETE_FEATURE_BUSINESS_DEPENDENCY = "DELETE FROM pluginwizard_plugin_feature_business WHERE id_business_class = ?";
    private static final String SQL_QUERY_INSERT_FEATURE_BUSINESS_DEPENDENCY = "INSERT INTO pluginwizard_plugin_feature_business ( id_plugin_feature, id_business_class ) VALUES ( ?, ?)";
    private static final String SQL_QUERY_CHECK_ATTRIBUTES_RELATED = "SELECT id_business_class FROM pluginwizard_plugin_business_attribute WHERE id_business_class= ?";
    private static final String SQL_QUERY_SELECTALL_BUSINESS_ID_BY_PLUGIN = "SELECT a.id_business_class FROM pluginwizard_plugin_business as a , " +
        "pluginwizard_plugin_feature_business as b , pluginwizard_plugin_id_feature as c where b.id_plugin_feature = c.id_plugin_feature" +
        " AND b.id_business_class=a.id_business_class AND c.id_plugin= ? ";
    private static final String SQL_QUERY_CHECK_BUSINESS_PRIMARY_KEY = "select count(d.attribute_name) FROM pluginwizard_plugin_feature_business as b ,pluginwizard_plugin_business_attribute as c,pluginwizard_plugin_attribute as d, pluginwizard_plugin_attribute_type as e WHERE b.id_business_class = ? AND b.id_business_class=c.id_business_class AND c.id_attribute=d.id_attribute AND d.attribute_type_id=e.attribute_type_id AND d.is_primary_key= 1";
    private static final String SQL_QUERY_CHECK_BUSINESS_DESCRIPTION = "select count(d.attribute_name) FROM pluginwizard_plugin_feature_business as b ,pluginwizard_plugin_business_attribute as c,pluginwizard_plugin_attribute as d, pluginwizard_plugin_attribute_type as e WHERE b.id_business_class = ? AND b.id_business_class=c.id_business_class AND c.id_attribute=d.id_attribute AND d.attribute_type_id=e.attribute_type_id AND d.is_description= 1";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * @param businessClass instance of the BusinessClass object to insert
     * @param plugin The plugin
     */
    public void insert( BusinessClass businessClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        businessClass.setIdBusinessClass( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, businessClass.getIdBusinessClass(  ) );
        daoUtil.setString( 2, businessClass.getBusinessClass(  ) );
        daoUtil.setString( 3, businessClass.getBusinessTableName(  ) );
        insertDependency( businessClass.getIdFeature(  ), businessClass.getIdBusinessClass(  ), plugin );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the businessClass from the table
     * @param nId The identifier of the businessClass
     * @param plugin The plugin
     * @return the instance of the BusinessClass
     */
    public BusinessClass load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        BusinessClass businessClass = null;

        if ( daoUtil.next(  ) )
        {
            businessClass = new BusinessClass(  );

            businessClass.setIdBusinessClass( daoUtil.getInt( 1 ) );
            businessClass.setBusinessClass( daoUtil.getString( 2 ) );
            businessClass.setBusinessTableName( daoUtil.getString( 3 ) );

            Collection<Attribute> listAttributes = selectAttributeList( nId, plugin );
            businessClass.setAttributes( listAttributes );
        }

        daoUtil.free(  );

        return businessClass;
    }

    /**
     * Delete a record from the table
     * @param nBusinessClassId The identifier of the businessClass
     * @param plugin The plugin
     */
    public void delete( int nBusinessClassId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nBusinessClassId );
        deleteDependency( nBusinessClassId, plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete the dependency
     * @param nBusinessClassId The identifier of the businessClass
     * @param plugin The plugin
     */
    public void deleteDependency( int nBusinessClassId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_FEATURE_BUSINESS_DEPENDENCY, plugin );
        daoUtil.setInt( 1, nBusinessClassId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
    * Delete the dependency
    * @param nIdPlugin The identifier of the businessClass
    * @param plugin The plugin
    */
    public void deleteAllClassesByPluginId( int nIdPlugin, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BUSINESS_ID_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            delete( daoUtil.getInt( 1 ), plugin );
        }

        daoUtil.free(  );
    }

    /**
     * Inserts the depedency between the business class and the admin feature
     * @param nIdPluginFeature The id of the plugin feature
     * @param nIdBusinessClass The id of the business class
     * @param plugin The plugin
     */
    public void insertDependency( int nIdPluginFeature, int nIdBusinessClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_FEATURE_BUSINESS_DEPENDENCY, plugin );

        daoUtil.setInt( 1, nIdPluginFeature );
        daoUtil.setInt( 2, nIdBusinessClass );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete business classes by feature
     * @param nFeatureId The identifier of the  feature
     * @param plugin The plugin
     */
    public void deleteByFeature( int nFeatureId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_FEATURE, plugin );
        daoUtil.setInt( 1, nFeatureId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Updates the record in the table
     * @param businessClass The reference of the businessClass
     * @param plugin The plugin
     */
    public void store( BusinessClass businessClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, businessClass.getIdBusinessClass(  ) );
        daoUtil.setString( 2, businessClass.getBusinessClass(  ) );
        daoUtil.setString( 3, businessClass.getBusinessTableName(  ) );

        daoUtil.setInt( 4, businessClass.getIdBusinessClass(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Returns a list of business classe
     * @param nPluginId The plugin id
     * @param plugin The plugin
     * @return A collection of business classes
     */
    public Collection<BusinessClass> selectBusinessClassesList( int nPluginId, Plugin plugin )
    {
        Collection<BusinessClass> businessClassList = new ArrayList<BusinessClass>(  );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeQuery(  );

        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );

        while ( daoUtil.next(  ) )
        {
            BusinessClass businessClass = new BusinessClass(  );
            businessClass.setIdFeature( daoUtil.getInt( 1 ) );

            int nIdBusinessClass = daoUtil.getInt( 2 );

            businessClass.setIdBusinessClass( nIdBusinessClass );
            businessClass.setBusinessClass( daoUtil.getString( 3 ) );
            businessClass.setBusinessTableName( daoUtil.getString( 4 ) );
            businessClass.setPackageName( pluginModel.getPluginName(  ) );
            businessClass.setPluginName( pluginModel.getPluginName(  ) );

            Collection<Attribute> listAttributes = selectAttributeList( nIdBusinessClass, plugin );
            businessClass.setAttributes( listAttributes );

            //Add the primary key and the description
            String strPrimaryAttributeName = AttributeHome.findPrimaryKey( nIdBusinessClass, plugin );
            String strDescriptionAttributeName = AttributeHome.findClassDescription( nIdBusinessClass, plugin );
            businessClass.setPrimaryKey( strPrimaryAttributeName );
            businessClass.setClassDescription( strDescriptionAttributeName );
            businessClassList.add( businessClass );
        }

        daoUtil.free(  );

        return businessClassList;
    }

    /**
    * Returns a list of Attribute
    * @param nBusinessClassId The business class id
    * @param plugin The plugin
    * @return A collection of attributes
    */
    public Collection<Attribute> selectAttributeList( int nBusinessClassId, Plugin plugin )
    {
        Collection<Attribute> listAttributes = new ArrayList<Attribute>(  );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ATTRIBUTES_BY_BUSINESS_CLASS_ID, plugin );
        daoUtil.setInt( 1, nBusinessClassId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Attribute attribute = new Attribute(  );
            attribute.setIdAttribute( daoUtil.getInt( 1 ) );
            attribute.setAttributeTypeId( daoUtil.getInt( 2 ) );
            attribute.setAttributeName( daoUtil.getString( 3 ) );
            attribute.setIsPrimary( daoUtil.getBoolean( 4 ) );
            attribute.setIsDescription( daoUtil.getBoolean( 5 ) );
            attribute.setType( daoUtil.getString( 6 ) );
            listAttributes.add( attribute );
        }

        daoUtil.free(  );

        return listAttributes;
    }

    /**
     * Returns a collection of business classes
     * @param nFeatureId The id of the feature
     * @param nPluginId The id of the plugin
     * @param plugin The Plugin
     * @return A collection of business classes selected by feature
     */
    public Collection<BusinessClass> selectBusinessClassesByFeature( int nFeatureId, int nPluginId, Plugin plugin )
    {
        Collection<BusinessClass> businessClassListByFeature = new ArrayList<BusinessClass>(  );
        Collection<BusinessClass> businessClassList = selectBusinessClassesList( nPluginId, plugin );

        for ( BusinessClass businessClass : businessClassList )
        {
            if ( businessClass.getIdFeature(  ) == nFeatureId )
            {
                businessClassListByFeature.add( businessClass );
            }
        }

        return businessClassListByFeature;
    }

    /**
     * Verifies whether key exists
     * @param nFeatureId the id of the feature
     * @param strBusinessClass The business class name
     * @param plugin The plugin
     * @return A boolean value which is true when class has attributes
     */
    public boolean keyExists( int nFeatureId, String strBusinessClass, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_COUNT_FIND_BY_FEATURE_AND_CLASS, plugin );
        daoUtil.setInt( 1, nFeatureId );
        daoUtil.setString( 2, strBusinessClass );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        if ( nCount > 0 )
        {
            bValue = true;
        }

        return bValue;
    }

    /**
     * Verifies whether business class has attributes
     * @param nBusinessClassId the id of the business class
     * @param plugin The plugin
     * @return A boolean value which is true when class has attributes
     */
    public boolean containsAttributes( int nBusinessClassId, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_ATTRIBUTES_RELATED, plugin );
        daoUtil.setInt( 1, nBusinessClassId );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        if ( nCount > 0 )
        {
            bValue = true;
        }

        return bValue;
    }

    /**
     * Verifies whether business class has a description
     * @param nIdBusinessClass the id of the business class
     * @param plugin The plugin
     * @return A boolean value which is true when class has a description
     */
    public boolean hasAlreadyDescription( int nIdBusinessClass, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_BUSINESS_DESCRIPTION, plugin );
        daoUtil.setInt( 1, nIdBusinessClass );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        if ( nCount > 0 )
        {
            bValue = true;
        }

        return bValue;
    }

    /**
     * Verifies whether business class has a description
     * @param nIdBusinessClass the id of the business class
     * @param plugin The plugin
     * @return A boolean value which is true when class has a key
     */
    public boolean hasAlreadyKey( int nIdBusinessClass, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_BUSINESS_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nIdBusinessClass );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        if ( nCount > 0 )
        {
            bValue = true;
        }

        return bValue;
    }
}
