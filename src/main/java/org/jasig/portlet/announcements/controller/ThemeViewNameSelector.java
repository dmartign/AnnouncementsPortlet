/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.announcements.controller;

import javax.portlet.PortletRequest;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.xml.namespace.QName;

import java.util.Comparator;

public class ThemeViewNameSelector implements IViewNameSelector {

    protected static final String THEME_NAME_PROPERTY = "themeName";
    protected static final String MOBILE_THEMES_KEY = "mobileThemes";
    protected static final String[] MOBILE_THEMES_DEFAULT = new String[]{ "UniversalityMobile" };
    protected static final String RESPONDR = "respondr";
    protected static final String[] RESPONDR_VALUE = new String[]{ "true" }; 

    private String mobileKey = "jqm";
    
    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }
    
    public String select(PortletRequest req, String baseViewName) {
        
        // Assertions.
        if (req == null) {
            String msg = "Argument 'req' cannot be null";
            throw new IllegalArgumentException(msg);
        }
       
        // respondr property to select theme to display  
        if (isRespondr(req)) { 
            return baseViewName;
        }
        else {
            if (isMobile(req)) {
                return baseViewName.concat(".").concat(mobileKey);
            } else {
                return baseViewName;
            }            
        }
        
    }
    
    protected boolean isMobile(PortletRequest request) {
        String[] mobileThemes = request.getPreferences().getValues(MOBILE_THEMES_KEY, MOBILE_THEMES_DEFAULT);
        String themeName = request.getProperty(THEME_NAME_PROPERTY);
        if (themeName == null) {
            return false;
        }
        
        for (String theme : mobileThemes) {
            if (themeName.equals(theme)) {
                return true;
            }
        }
        
        return false;
    }
 
    // Adds portlet preference for respondr 
    protected boolean isRespondr(PortletRequest respRequest) {
        String[] respondrReq = respRequest.getPreferences().getValues(RESPONDR, RESPONDR_VALUE);
        if (respondrReq.equals("respondr")) {
            return true;
        }
        else {
            return false;
        }
    }

}
