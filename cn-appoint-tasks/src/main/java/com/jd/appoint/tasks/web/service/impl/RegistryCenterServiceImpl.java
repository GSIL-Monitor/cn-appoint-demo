/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.jd.appoint.tasks.web.service.impl;

import com.google.common.base.Optional;
import com.jd.appoint.domain.conf.ConfigurationCenter;
import com.jd.appoint.tasks.web.service.JobApiService;
import com.jd.appoint.tasks.web.service.RegistryCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service("registryCenterService")
public class RegistryCenterServiceImpl implements RegistryCenterService {

    @Autowired
    private JobApiService jobAPIService;
    

    public Collection<ConfigurationCenter> loadAll() {
        ConfigurationCenter configurationCenter = jobAPIService.getRegistry();
        Set<ConfigurationCenter> configurationCenterSet = new HashSet<ConfigurationCenter>();
        configurationCenterSet.add(configurationCenter);
        return configurationCenterSet;
    }
    
    public ConfigurationCenter load(final String name) {
        ConfigurationCenter configurationCenter = jobAPIService.getRegistry();
        return configurationCenter;
    }

    
    public Optional<ConfigurationCenter> loadActivated() {
        ConfigurationCenter configurationCenter = jobAPIService.getRegistry();
        return Optional.of(configurationCenter);
    }
    

    public boolean add(final ConfigurationCenter config) {
        return true;
    }

    @Override
    public void delete(String name) {

    }
}
