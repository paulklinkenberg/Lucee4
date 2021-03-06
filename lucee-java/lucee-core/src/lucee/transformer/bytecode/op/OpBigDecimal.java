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
package lucee.transformer.bytecode.op;

import java.math.BigDecimal;

import lucee.transformer.bytecode.BytecodeContext;
import lucee.transformer.bytecode.BytecodeException;
import lucee.transformer.bytecode.expression.Expression;
import lucee.transformer.bytecode.expression.ExpressionBase;
import lucee.transformer.bytecode.util.Types;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

public final class OpBigDecimal extends ExpressionBase {

	private static final Method TO_BIG_DECIMAL = new Method("toBigDecimal",Types.BIG_DECIMAL,new Type[]{Types.OBJECT});
	
	private static final Method _ADD = new Method("add",Types.BIG_DECIMAL,new Type[]{Types.BIG_DECIMAL});
	private static final Method _SUBSTRACT = new Method("subtract",Types.BIG_DECIMAL,new Type[]{Types.BIG_DECIMAL});
	private static final Method _DIVIDE= new Method("divide",Types.BIG_DECIMAL,new Type[]{Types.BIG_DECIMAL,Types.INT_VALUE,Types.INT_VALUE});
	private static final Method _MULTIPLY= new Method("multiply",Types.BIG_DECIMAL,new Type[]{Types.BIG_DECIMAL});
	private static final Method _REMAINER= new Method("remainder",Types.BIG_DECIMAL,new Type[]{Types.BIG_DECIMAL});
	
    private int operation;
	private Expression left;
	private Expression right;

    public OpBigDecimal(Expression left, Expression right, int operation)  {
        super(left.getStart(),right.getEnd());
        this.left=	left;
        this.right=	right;   
        this.operation=operation;
    }
    

	/**
     *
     * @see lucee.transformer.bytecode.expression.ExpressionBase#_writeOut(org.objectweb.asm.commons.GeneratorAdapter, int)
     */
    public Type _writeOut(BytecodeContext bc, int mode) throws BytecodeException {
    	return writeOutDouble(bc, mode) ;
    }
    
    public Type writeOutDouble(BytecodeContext bc, int mode) throws BytecodeException {

        if(operation==OpDouble.EXP) {
        	return new OpDouble(left, right, operation).writeOutDouble(bc, mode);
        }
    	
    	GeneratorAdapter adapter = bc.getAdapter();
    	
    	
    	toBigDecimal(bc,left);
    	toBigDecimal(bc,right);
    	
    	
    	//Caster.toBigDecimal("1").add(Caster.toBigDecimal("1"));
        if(operation==OpDouble.PLUS) {
        	adapter.invokeVirtual(Types.BIG_DECIMAL, _ADD);
        }
        else if(operation==OpDouble.MINUS) {
        	adapter.invokeVirtual(Types.BIG_DECIMAL, _SUBSTRACT);
        }
        else if(operation==OpDouble.DIVIDE) {
        	adapter.push(34);
        	adapter.push( BigDecimal.ROUND_HALF_EVEN);
        	adapter.invokeVirtual(Types.BIG_DECIMAL, _DIVIDE);
        }
        else if(operation==OpDouble.INTDIV) {
        	adapter.push(0);
        	adapter.push( BigDecimal.ROUND_DOWN);
        	adapter.invokeVirtual(Types.BIG_DECIMAL, _DIVIDE);
        }
        else if(operation==OpDouble.MULTIPLY) {
        	adapter.invokeVirtual(Types.BIG_DECIMAL, _MULTIPLY);
        }
        
        else if(operation==OpDouble.MODULUS) {
        	adapter.invokeVirtual(Types.BIG_DECIMAL, _REMAINER);
        }
        return Types.BIG_DECIMAL;
    }
    


	private static void toBigDecimal(BytecodeContext bc, Expression expr) throws BytecodeException {
		expr.writeOut(bc,MODE_REF);
    	if(expr instanceof OpBigDecimal) return;
    	bc.getAdapter().invokeStatic(Types.CASTER,TO_BIG_DECIMAL);
	}


	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}


}