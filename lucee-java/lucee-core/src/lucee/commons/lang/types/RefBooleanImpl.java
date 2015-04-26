/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.commons.lang.types;

/**
 * Integer Type that can be modified
 */
public final class RefBooleanImpl implements RefBoolean {//MUST add interface Castable

    private boolean value;


    public RefBooleanImpl() {}
    
    /**
     * @param value
     */
    public RefBooleanImpl(boolean value) {
        this.value=value;
    }
    
    /**
     * @param value
     */
    public void setValue(boolean value) {
        this.value = value;
    }
    
    /**
     * @return returns value as Boolean Object
     */
    public Boolean toBoolean() {
        return value?Boolean.TRUE:Boolean.FALSE;
    }
    
    /**
     * @return returns value as boolean value
     */
    public boolean toBooleanValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value?"true":"false";
    }
}