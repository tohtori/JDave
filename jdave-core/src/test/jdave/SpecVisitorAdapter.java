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
package jdave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdave.runner.ISpecVisitor;
import jdave.runner.Context;
import jdave.runner.IBehaviorResults;
import jdave.runner.Behavior;

/**
 * @author Joni Freeman
 */
public class SpecVisitorAdapter implements ISpecVisitor {
    private final IBehaviorResults results;
    private List<String> contextNames = new ArrayList<String>();

    public SpecVisitorAdapter(IBehaviorResults results) {
        this.results = results;        
    }
    
    public void onContext(Context context) {
        contextNames.add(context.getName());
    }
    
    public void onBehavior(Behavior method) throws Exception {
        method.run(results);
    }
    
    public List<String> getContextNames() {
        Collections.sort(contextNames);
        return contextNames;
    }
}
