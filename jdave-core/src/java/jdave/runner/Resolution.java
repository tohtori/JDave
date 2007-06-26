/*
 * Copyright 2007 the original author or authors.
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
package jdave.runner;

import java.util.Collection;

import jdave.support.Assert;

/**
 * @author Joni Freeman
 */
public class Resolution {
    private final Groups groups;

    public Resolution(Groups groups) {
        Assert.notNull(groups, "must include @Groups annotation");
        this.groups = groups;
    }

    public boolean includes(Collection<String> groupsToCheck) {
        for (String groupToInclude : groups.include()) {
            if (includes(groupsToCheck, groupToInclude)) {
                return !excludes(groupsToCheck);
            }
        }
        return false;
    }

    private boolean excludes(Collection<String> groupsToCheck) {
        for (String groupToExclude : groups.exclude()) {
            if (groupToExclude.equals(Groups.ALL)) {
                return true;
            }
            if (groupsToCheck.contains(groupToExclude)) {
                return true;
            }
        }
        return false;
    }

    private boolean includes(Collection<String> groupsToCheck, String groupToInclude) {
        if (groupToInclude.equals(Groups.ALL)) {
            return true;
        }
        return groupsToCheck.contains(groupToInclude);
    }
}
