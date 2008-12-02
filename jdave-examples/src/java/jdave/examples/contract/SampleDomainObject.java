/*
 * Copyright 2006 the original author or authors.
 *
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
 */
package jdave.examples.contract;

import java.io.Serializable;

/**
 * @author Joni Freeman
 */
public class SampleDomainObject implements Serializable, Cloneable, Comparable<SampleDomainObject> {
    private final Integer id;
    private String name;

    public SampleDomainObject(Integer id) {
        this.id = id;        
    }

    public SampleDomainObject setName(String name) {
        this.name = name;
        return this;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj.getClass().equals(SampleDomainObject.class))) {
            return false;
        }
        SampleDomainObject other = (SampleDomainObject) obj;
        return id.equals(other.id);
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    public int compareTo(SampleDomainObject o) {
        if (name.equals(o.name)) {
            if (equals(o)) {
                return 0;
            }
            return id - o.id;
        }
        return name.compareTo(o.name);
    }
    
    @Override
    public SampleDomainObject clone() {
        try {
            return (SampleDomainObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
