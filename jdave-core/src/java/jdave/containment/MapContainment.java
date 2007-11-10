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
package jdave.containment;

import java.util.Map;

import jdave.ExpectationFailedException;
import jdave.support.Assert;

/**
 * @author Joni Freeman
 */
public class MapContainment {
    private final Object[] keys;
    private Object[] values;

    public MapContainment(Object... keys) {
        this.keys = keys;
    }

    public MapContainment to(Object... values) {
        Assert.isTrue(keys.length == values.length, "number of keys " + keys.length + " does not match number of values " + values.length);
        this.values = values;
        return this;
    }

    public void verify(Map<?, ?> map) {
        for (int i = 0; i < keys.length; i++) {
            if (!map.get(keys[i]).equals(values[i])) {
               throw new ExpectationFailedException("no mapping " + keys[i] + " -> " + values[i]);
            }
        }
    }
}
