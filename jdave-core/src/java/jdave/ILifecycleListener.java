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
package jdave;

/**
 * A listener which will get notifications when new contexts are created.
 * 
 * @see Specification#addListener(ILifecycleListener)
 * 
 * @author Joni Freeman
 */
public interface ILifecycleListener {
    /**
     * Called just after a context has been instantiated.
     * 
     * @param contextInstance the instantiated context
     */
    void afterContextInstantiation(Object contextInstance);
    
    /**
     * Called just after context's <code>create</code> method has been called.
     * 
     * @param contextInstance the holding context
     * @param createdContext the object which was returned from <code>create</code> method
     */
    void afterContextCreation(Object contextInstance, Object createdContext);
}
