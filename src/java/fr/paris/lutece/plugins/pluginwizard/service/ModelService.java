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
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.Model;
import fr.paris.lutece.plugins.pluginwizard.business.ModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Application;
import fr.paris.lutece.plugins.pluginwizard.business.model.Attribute;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import java.util.ArrayList;
import java.util.List;

/**
 * Model Service provides all plugin'model manipulations
 */
public class ModelService
{

    public static void createModel(String strPluginName)
    {
        Model model = new Model();
        model.setName(strPluginName);
        PluginModel pm = new PluginModel();
        pm.setPluginName(strPluginName);
        model = ModelHome.create(model);
        pm.setIdPlugin(model.getIdPlugin());
        savePluginModel(pm);
    }

    /**
     *
     * @param nPluginId The plugin's ID
     * @return
     */
    public static PluginModel getPluginModel(int nPluginId)
    {
        PluginModel pm;
        Model model = ModelHome.findByPrimaryKey(nPluginId);
        System.out.println(" #### GetPluginModel : id : " + nPluginId + " model : " + model);
        if (model != null)
        {
            pm = MapperService.readJson(model.getModelJson());
        }
        else
        {
            pm = new PluginModel();
            pm.setIdPlugin(nPluginId);
        }
        return pm;

    }

    public static void savePluginModel(PluginModel pm)
    {
        Model model = new Model();
        model.setIdPlugin(pm.getIdPlugin());
        model.setName(pm.getPluginName());
        String strJson = MapperService.getJson(pm);
        model.setModelJson(strJson);
        System.out.println("#### save pm : " + strJson);
        if (ModelHome.findByPrimaryKey(pm.getIdPlugin()) != null)
        {
            ModelHome.update(model);
        }
        else
        {
            ModelHome.create(model);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // FEATURES
    /**
     * Add a feature
     *
     * @param nPluginId The plugin's ID
     * @param feature The feature
     */
    public static void addFeature(int nPluginId, Feature feature)
    {
        PluginModel pm = getPluginModel(nPluginId);
        feature.setId(getMaxFeatureId(pm) + 1);
        pm.getFeatures().add(feature);
        savePluginModel(pm);

    }

    /**
     * Get a given feature
     *
     * @param nPluginId The plugin's ID
     * @param nFeatureId The feature's ID
     * @return The feature
     */
    public static Feature getFeature(int nPluginId, int nFeatureId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        for (Feature feature : pm.getFeatures())
        {
            if (feature.getId() == nFeatureId)
            {
                return feature;
            }
        }
        return null;

    }

    /**
     * Get The max feature ID
     *
     * @param pm The Plugin Model
     * @return The max used ID
     */
    private static int getMaxFeatureId(PluginModel pm)
    {
        int nMax = 0;
        for (Feature feature : pm.getFeatures())
        {
            if (feature.getId() > nMax)
            {
                nMax = feature.getId();
            }
        }
        return nMax;
    }

    /**
     * Update a feature
     *
     * @param nPluginId The plugin's ID
     * @param feature The feature
     */
    public static void updateFeature(int nPluginId, Feature feature)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<Feature> list = pm.getFeatures();
        for (int i = 0; i < list.size(); i++)
        {
            Feature f = list.get(i);
            if (f.getId() == feature.getId())
            {
                list.set(i, feature);
                savePluginModel(pm);
                break;
            }
        }
    }

    /**
     * Remove a feature
     *
     * @param nPluginId The plugin's ID
     * @param nFeatureId The feature's ID
     */
    public static void removeFeature(int nPluginId, int nFeatureId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<Feature> list = pm.getFeatures();
        for (int i = 0; i < list.size(); i++)
        {
            Feature f = list.get(i);
            if (f.getId() == nFeatureId)
            {
                list.remove(i);
                savePluginModel(pm);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // APPLICATIONS
    /**
     * Get a given application
     *
     * @param nPluginId The plugin's ID
     * @param nApplicationId
     * @return
     */
    public static Application getApplication(int nPluginId, int nApplicationId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        for (Application application : pm.getApplications())
        {
            if (application.getId() == nApplicationId)
            {
                return application;
            }
        }
        return null;
    }

    /**
     * Add an application to the model
     *
     * @param nPluginId The plugin's ID
     * @param application The application
     */
    public static void addApplication(int nPluginId, Application application)
    {
        PluginModel pm = getPluginModel(nPluginId);
        application.setId(getMaxApplicationId(pm) + 1);
        pm.getApplications().add(application);
        savePluginModel(pm);
    }

    /**
     * Get The max application ID
     *
     * @param pm The Plugin Model
     * @return The max used ID
     */
    private static int getMaxApplicationId(PluginModel pm)
    {
        int nMax = 0;
        for (Application application : pm.getApplications())
        {
            if (application.getId() > nMax)
            {
                nMax = application.getId();
            }
        }
        return nMax;
    }

    /**
     * Update an application
     *
     * @param nPluginId The plugin's ID
     * @param application The application
     */
    public static void updateApplication(int nPluginId, Application application)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<Application> list = pm.getApplications();
        for (int i = 0; i < list.size(); i++)
        {
            Application app = list.get(i);
            if (app.getId() == application.getId())
            {
                list.set(i, application);
                savePluginModel(pm);
                break;
            }
        }
    }

    /**
     * Remove an application
     *
     * @param nPluginId The plugin's ID
     * @param nApplicationId The application's ID
     */
    public static void removeApplication(int nPluginId, int nApplicationId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<Application> list = pm.getApplications();
        for (int i = 0; i < list.size(); i++)
        {
            Application f = list.get(i);
            if (f.getId() == nApplicationId)
            {
                list.remove(i);
                savePluginModel(pm);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // PORTLET
    /**
     * Get a given portlet
     *
     * @param nPluginId The plugin's ID
     * @param nPortletId
     * @return
     */
    public static Portlet getPortlet(int nPluginId, int nPortletId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        for (Portlet portlet : pm.getPortlets())
        {
            if (portlet.getId() == nPortletId)
            {
                return portlet;
            }
        }
        return null;
    }

    /**
     * Add an portlet to the model
     *
     * @param nPluginId The plugin's ID
     * @param portlet The portlet
     */
    public static void addPortlet(int nPluginId, Portlet portlet)
    {
        PluginModel pm = getPluginModel(nPluginId);
        portlet.setId(getMaxPortletId(pm) + 1);
        pm.getPortlets().add(portlet);
        savePluginModel(pm);
    }

    /**
     * Get The max portlet ID
     *
     * @param pm The Plugin Model
     * @return The max used ID
     */
    private static int getMaxPortletId(PluginModel pm)
    {
        int nMax = 0;
        for (Portlet portlet : pm.getPortlets())
        {
            if (portlet.getId() > nMax)
            {
                nMax = portlet.getId();
            }
        }
        return nMax;
    }

    /**
     * Update an portlet
     *
     * @param nPluginId The plugin's ID
     * @param portlet The portlet
     */
    public static void updatePortlet(int nPluginId, Portlet portlet)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<Portlet> list = pm.getPortlets();
        for (int i = 0; i < list.size(); i++)
        {
            Portlet p = list.get(i);
            if (p.getId() == portlet.getId())
            {
                list.set(i, portlet);
                savePluginModel(pm);
                break;
            }
        }
    }

    /**
     * Remove an portlet
     *
     * @param nPluginId The plugin's ID
     * @param nPortletId The portlet's ID
     */
    public static void removePortlet(int nPluginId, int nPortletId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<Portlet> list = pm.getPortlets();
        for (int i = 0; i < list.size(); i++)
        {
            Portlet p = list.get(i);
            if (p.getId() == nPortletId)
            {
                list.remove(i);
                savePluginModel(pm);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // BUSINESS CLASSES
    /**
     * Get a given bc
     *
     * @param nPluginId The plugin's ID
     * @param nBusinessClassId
     * @return
     */
    public static BusinessClass getBusinessClass(int nPluginId, int nBusinessClassId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        return getBusinessClass(pm, nBusinessClassId);
    }

    private static BusinessClass getBusinessClass(PluginModel pm, int nBusinessClassId)
    {
        for (BusinessClass bc : pm.getBusinessClasses())
        {
            if (bc.getId() == nBusinessClassId)
            {
                return bc;
            }
        }
        return null;

    }

    /**
     * Add an bc to the model
     *
     * @param nPluginId The plugin's ID
     * @param bc The bc
     * @return The business class with its ID
     */
    public static BusinessClass addBusinessClass(int nPluginId, BusinessClass bc)
    {
        PluginModel pm = getPluginModel(nPluginId);
        bc.setId(getMaxBusinessClassId(pm) + 1);
        pm.getBusinessClasses().add(bc);
        savePluginModel(pm);
        return bc;
    }

    /**
     * Get The max bc ID
     *
     * @param pm The Plugin Model
     * @return The max used ID
     */
    private static int getMaxBusinessClassId(PluginModel pm)
    {
        int nMax = 0;
        for (BusinessClass bc : pm.getBusinessClasses())
        {
            if (bc.getId() > nMax)
            {
                nMax = bc.getId();
            }
        }
        return nMax;
    }

    /**
     * Update an businessClass
     *
     * @param nPluginId The plugin's ID
     * @param businessClass The businessClass
     */
    public static void updateBusinessClass(int nPluginId, BusinessClass businessClass)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<BusinessClass> list = pm.getBusinessClasses();
        for (int i = 0; i < list.size(); i++)
        {
            BusinessClass bc = list.get(i);
            if (bc.getId() == businessClass.getId())
            {
                list.set(i, bc);
                savePluginModel(pm);
                break;
            }
        }
    }

    /**
     * Remove an bc
     *
     * @param nPluginId The plugin's ID
     * @param nBusinessClassId The bc's ID
     */
    public static void removeBusinessClass(int nPluginId, int nBusinessClassId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        List<BusinessClass> list = pm.getBusinessClasses();
        for (int i = 0; i < list.size(); i++)
        {
            BusinessClass bc = list.get(i);
            if (bc.getId() == nBusinessClassId)
            {
                list.remove(i);
                savePluginModel(pm);
                break;
            }
        }
    }

    public static void removeAll(int nPluginId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        pm.getApplications().clear();
        pm.getBusinessClasses().clear();
        pm.getFeatures().clear();
        pm.getPortlets().clear();
        savePluginModel(pm);
    }

    ////////////////////////////////////////////////////////////////////////////
    // BUSINESS CLASS ATTRIBUTES
    /**
     * Get a given attribute
     *
     * @param nPluginId The plugin's ID
     * @param nBusinessClassId The business class ID
     * @param nAttributeId
     * @return
     */
    public static Attribute getAttribute(int nPluginId, int nBusinessClassId, int nAttributeId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        BusinessClass bc = getBusinessClass(pm, nBusinessClassId);
        List<Attribute> listAttributes = bc.getAttributes();
        for (Attribute attribute : listAttributes)
        {
            if (attribute.getId() == nAttributeId)
            {
                return attribute;
            }
        }
        return null;
    }

    /**
     * Add an attribute to a business class
     *
     * @param nPluginId The plugin's ID
     * @param nBusinessClassId The business class ID
     * @param attribute The Attribute
     */
    public static void addAttribute(int nPluginId, int nBusinessClassId, Attribute attribute)
    {
        PluginModel pm = getPluginModel(nPluginId);
        BusinessClass bc = getBusinessClass(pm, nBusinessClassId);
        List<Attribute> listAttributes = bc.getAttributes();
        attribute.setId(getMaxAttributeId(listAttributes) + 1);
        attribute.setType(getAttributeType(attribute.getAttributeTypeId()));
        if (attribute.getIsPrimary())
        {
            bc.setPrimaryKey(attribute.getAttributeName());
        }
        if (attribute.getIsDescription())
        {
            bc.setClassDescription(attribute.getAttributeName());
        }
        listAttributes.add(attribute);
        savePluginModel(pm);
    }

    /**
     * Get The max attribute ID
     *
     * @param pm The Plugin Model
     * @return The max used ID
     */
    private static int getMaxAttributeId(List<Attribute> listAttributes)
    {
        int nMax = 0;
        for (Attribute attribute : listAttributes)
        {
            if (attribute.getId() > nMax)
            {
                nMax = attribute.getId();
            }
        }
        return nMax;
    }

    /**
     * Update an attribute
     *
     * @param nPluginId The plugin's ID
     * @param nBusinessClassId The business class ID
     * @param attribute The attribute
     */
    public static void updateAttribute(int nPluginId, int nBusinessClassId, Attribute attribute)
    {
        PluginModel pm = getPluginModel(nPluginId);
        BusinessClass bc = getBusinessClass(pm, nBusinessClassId);
        List<Attribute> list = bc.getAttributes();
        for (int i = 0; i < list.size(); i++)
        {
            Attribute attr = list.get(i);
            if (attr.getId() == attribute.getId())
            {
                list.set(i, attribute);
                if (attribute.getIsPrimary())
                {
                    bc.setPrimaryKey(attribute.getAttributeName());
                }
                if (attribute.getIsDescription())
                {
                    bc.setClassDescription(attribute.getAttributeName());
                }
                savePluginModel(pm);
                break;
            }
        }
    }

    /**
     * Remove an attribute
     *
     * @param nPluginId The plugin's ID
     * @param nBusinessClassId The business class ID
     * @param nAttributeId The attribute's ID
     */
    public static void removeAttribute(int nPluginId, int nBusinessClassId, int nAttributeId)
    {
        PluginModel pm = getPluginModel(nPluginId);
        BusinessClass bc = getBusinessClass(pm, nBusinessClassId);
        List<Attribute> list = bc.getAttributes();
        for (int i = 0; i < list.size(); i++)
        {
            Attribute attr = list.get(i);
            if (attr.getId() == nAttributeId)
            {
                list.remove(i);
                savePluginModel(pm);
                break;
            }
        }
    }

    public static List<BusinessClass> getBusinessClassesByFeature(PluginModel pm, int nFeatureId)
    {
        List<BusinessClass> list = new ArrayList<BusinessClass>();
        List<BusinessClass> listAll = pm.getBusinessClasses();
        for (BusinessClass bc : listAll)
        {
            if (bc.getIdFeature() == nFeatureId)
            {
                list.add(bc);
            }
        }
        return list;
    }

    public static ReferenceList getComboFeatures(int nPluginId)
    {
        ReferenceList list = new ReferenceList();

        for (Feature feature : getPluginModel(nPluginId).getFeatures())
        {
            list.addItem(feature.getId(), feature.getPluginFeatureTitle());
        }
        return list;
    }

    private static String getAttributeType(int nAttributeTypeId)
    {
        for (ReferenceItem item : getAttributeTypes())
        {
            if (item.getCode().equals(Integer.toString(nAttributeTypeId)))
            {
                return item.getName();
            }
        }
        throw new AppException("Invalide Attribute type");
    }

    public static ReferenceList getAttributeTypes()
    {
        // FIXME load list from spring context
        ReferenceList list = new ReferenceList();
        list.addItem(1, "int");
        list.addItem(2, "String");
        return list;

    }
}
