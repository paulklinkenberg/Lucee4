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
package lucee.transformer.bytecode.visitor;

import lucee.transformer.bytecode.BytecodeContext;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.GeneratorAdapter;

public final class AndVisitor {
	private Label end;
	private Label l2;
	public void visitBegin() {
		end = new Label();
    	l2 = new Label();
	}
	public void visitMiddle(BytecodeContext bc) {
		bc.getAdapter().ifZCmp(Opcodes.IFEQ, end);

		
		
	}
	public void visitEnd(BytecodeContext bc) {
		GeneratorAdapter adapter = bc.getAdapter();
    	adapter.ifZCmp(Opcodes.IFEQ, end);
    	adapter.push(true);
    	
    	adapter.visitJumpInsn(Opcodes.GOTO, l2);
    	adapter.visitLabel(end);

    	adapter.push(false);
    	adapter.visitLabel(l2);
	}
}
