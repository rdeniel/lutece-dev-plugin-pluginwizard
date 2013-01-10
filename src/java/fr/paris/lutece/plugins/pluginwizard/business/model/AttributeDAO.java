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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for Attribute objects
 */

/**
 * @author nobaudv
 *
 */
public final class AttributeDAO implements IAttributeDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_attribute ) FROM pluginwizard_plugin_attribute";
    private static final String SQL_QUERY_SELECT = "SELECT id_attribute, attribute_type_id, attribute_name, is_primary_key , is_description FROM pluginwizard_plugin_attribute WHERE id_attribute = ?";
    private static final String SQL_QUERY_SELECT_TYPE_BY_ATTRIBUE_ID = "SELECT b.attribute_type_name FROM pluginwizard_plugin_attribute as a,pluginwizard_plugin_attribute_type as b WHERE id_attribute = ? AND a.attribute_type_id=b.attribute_type_id";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_attribute ( id_attribute, attribute_type_id, attribute_name ,is_primary_key , is_description ) VALUES ( ?, ?, ? ,?, ?) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_attribute WHERE id_attribute = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_attribute SET id_attribute = ?, attribute_type_id = ?, attribute_name = ? , is_primary_key = ? , is_description =  ? WHERE id_attribute = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_attribute, attribute_type_id, attribute_name FROM pluginwizard_plugin_attribute";
    private static final String SQL_QUERY_SELECTALL_ATTTIBUTE_TYPE_COMBO = "select attribute_type_id,attribute_type_name from pluginwizard_plugin_attribute_type";
    private static final String SQL_QUERY_DELETE_BUSINESS_ATTRIBUTE_DEPENDENCY = "DELETE FROM pluginwizard_plugin_business_attribute WHERE id_attribute= ?";
    private static final String SQL_QUERY_INSERT_BUSINESS_ATTRIBUTE_DEPENDENCY = "INSERT INTO pluginwizard_plugin_business_attribute ( id_business_class , id_attribute ) VALUES ( ?, ?)";
    private static final String SQL_QUERY_SELECTALL_ATTRIBUTE_ID_BY_PLUGIN = "SELECT d.id_attribute FROM pluginwizard_plugin_business as a ," +
        "pluginwizard_plugin_feature_business as b , pluginwizard_plugin_id_feature as c ,pluginwizard_plugin_business_attribute as d " +
        "where b.id_plugin_feature = c.id_plugin_feature AND b.id_business_class=a.id_business_class AND" +
        " d.id_business_class=a.id_business_class AND c.id_plugin= ? ";
    private static final String SQL_QUERY_SELECT_CLASS_KEY_BY_CLASS_ID = "select attribute_name FROM pluginwizard_plugin_attribute as a , pluginwizard_plugin_business_attribute as b  WHERE a.id_attribute=b.id_attribute and b.id_business_class= ? and a.is_primary_key=1";
    private static final String SQL_QUERY_SELECT_CLASS_DESCRIPTION_BY_CLASS_ID = "select attribute_name FROM pluginwizard_plugin_attribute as a , pluginwizard_plugin_business_attribute as b  WHERE a.id_attribute=b.id_attribute and b.id_business_class=? and a.is_description=1";

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
     * @param nBusinessId The id of the business class
     * @param attribute instance of the Attribute object to insert
     * @param plugin The plugin
     */
    public void insert( int nBusinessId, Attribute attribute, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        attribute.setIdAttribute( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, attribute.getIdAttribute(  ) );
        daoUtil.setInt( 2, attribute.getAttributeTypeId(  ) );
        daoUtil.setString( 3, attribute.getAttributeName(  ) );
        daoUtil.setBoolean( 4, attribute.getIsPrimary(  ) );
        daoUtil.setBoolean( 5, attribute.getIsDescription(  ) );
        insertDependency( nBusinessId, attribute.getIdAttribute(  ), plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Inserts the dependency between the business class and the attributes
     * @param nBusinessClassId The business class id
     * @param nAttributeId The attribute identifier
     * @param plugin The plugin
     */
    public void insertDependency( int nBusinessClassId, int nAttributeId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_BUSINESS_ATTRIBUTE_DEPENDENCY, plugin );

        daoUtil.setInt( 1, nBusinessClassId );
        daoUtil.setInt( 2, nAttributeId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the attribute from the table
     * @param nId The identifier of the attribute
     * @param plugin The plugin
     * @return the instance of the Attribute
     */
    public Attribute load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        Attribute attribute = null;

        if ( daoUtil.next(  ) )
        {
            attribute = new Attribute(  );

            attribute.setIdAttribute( daoUtil.getInt( 1 ) );
            attribute.setAttributeTypeId( daoUtil.getInt( 2 ) );
            attribute.setAttributeName( daoUtil.getString( 3 ) );
            attribute.setIsPrimary( daoUtil.getInt( 4 ) != 0 );
            attribute.setIsDescription( daoUtil.getInt( 5 ) != 0 );
        }

        daoUtil.free(  );

        return attribute;
    }

    /**
     * Delete a record from the table
     * @param nAttributeId The identifier of the attribute
     * @param plugin The plugin
     */
    public void delete( int nAttributeId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nAttributeId );
        deleteDependency( nAttributeId, plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
    * Delete the dependency
    * @param nIdAttribute The identifier of the attribute
    * @param plugin The plugin
    */
    public void deleteDependency( int nIdAttribute, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BUSINESS_ATTRIBUTE_DEPENDENCY, plugin );
        daoUtil.setInt( 1, nIdAttribute );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param attribute The reference of the attribute
     * @param plugin The plugin
     */
    public void store( Attribute attribute, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, attribute.getIdAttribute(  ) );
        daoUtil.setInt( 2, attribute.getAttributeTypeId(  ) );
        daoUtil.setString( 3, attribute.getAttributeName(  ) );
        daoUtil.setBoolean( 4, attribute.getIsPrimary(  ) );
        daoUtil.setBoolean( 5, attribute.getIsDescription(  ) );
        daoUtil.setInt( 6, attribute.getIdAttribute(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the attributes and returns them as a List
     * @param plugin The plugin
     * @return The List which contains the data of all the attributes
     */
    public List<Attribute> selectAttributesList( Plugin plugin )
    {
        List<Attribute> attributeList = new ArrayList<Attribute>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Attribute attribute = new Attribute(  );

            attribute.setIdAttribute( daoUtil.getInt( 1 ) );
            attribute.setAttributeTypeId( daoUtil.getInt( 2 ) );
            attribute.setAttributeName( daoUtil.getString( 3 ) );

            attributeList.add( attribute );
        }

        daoUtil.free(  );

        return attributeList;
    }

    /**
     * Fetches the type name of an attribute
     * @param nIdAttribute The id of the attribute
     * @param plugin The plugin
     * @return The attribute type name
     */
    public String getTypeName( int nIdAttribute, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_TYPE_BY_ATTRIBUE_ID, plugin );
        daoUtil.setInt( 1, nIdAttribute );
        daoUtil.executeQuery(  );

        String strType = "";

        if ( daoUtil.next(  ) )
        {
            strType = daoUtil.getString( 1 );
        }

        daoUtil.free(  );

        return strType;
    }

    /**
     * Load the list of types of the attribute for the business class
     * @return The Collection of the attribute types
     * @param plugin The plugin
     */
    public ReferenceList selectAttributeListCombo( Plugin plugin )
    {
        ReferenceList attributeList = new ReferenceList(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ATTTIBUTE_TYPE_COMBO, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            attributeList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return attributeList;
    }

    /**
    * Deletes all the attribute related to a plugin
    * @param nIdPlugin The id of the plugin
    * @param plugin The plugin
    */
    public void deleteAllAttributesByPluginId( int nIdPlugin, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ATTRIBUTE_ID_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            delete( daoUtil.getInt( 1 ), plugin );
        }

        daoUtil.free(  );
    }

    /**
     * Returns the string representation of a class
     * @param nIdBusinessClass The id of the business class
     * @param plugin The plugin
     * @return The business class description
     */
    public String selectClassDescription( int nIdBusinessClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CLASS_DESCRIPTION_BY_CLASS_ID, plugin );
        daoUtil.setInt( 1, nIdBusinessClass );
        daoUtil.executeQuery(  );

        String strClassDescription = "";

        if ( daoUtil.next(  ) )
        {
            strClassDescription = daoUtil.getString( 1 );
        }

        daoUtil.free(  );

        return strClassDescription;
    }

    /**
     * Fetches the primary key of a business class
     * @param nIdBusinessClass The id of the business class
     * @param plugin The plugin
     * @return The string representation of the key of the business class
     */
    public String selectPrimaryKey( int nIdBusinessClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CLASS_KEY_BY_CLASS_ID, plugin );
        daoUtil.setInt( 1, nIdBusinessClass );
        daoUtil.executeQuery(  );

        String strPrimaryKey = "";

        if ( daoUtil.next(  ) )
        {
            strPrimaryKey = daoUtil.getString( 1 );
        }

        daoUtil.free(  );

        return strPrimaryKey;
    }
}
