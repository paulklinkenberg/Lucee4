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
package lucee.transformer.bytecode.statement.tag;

import lucee.transformer.bytecode.BytecodeContext;
import lucee.transformer.bytecode.BytecodeException;
import lucee.transformer.bytecode.Position;
import lucee.transformer.bytecode.expression.Expression;
import lucee.transformer.bytecode.statement.FlowControlBreak;
import lucee.transformer.bytecode.statement.FlowControlContinue;
import lucee.transformer.bytecode.visitor.WhileVisitor;

import org.objectweb.asm.Label;

public final class TagWhile extends TagBaseNoFinal implements FlowControlBreak,FlowControlContinue {

	private WhileVisitor wv;
	private String label;

	public TagWhile(Position start,Position end) {
		super(start,end);
	}


	/**
	 * @see lucee.transformer.bytecode.statement.StatementBase#_writeOut(org.objectweb.asm.commons.GeneratorAdapter)
	 */
	public void _writeOut(BytecodeContext bc) throws BytecodeException {
		wv = new WhileVisitor();
		wv.visitBeforeExpression(bc);
			getAttribute("condition").getValue().writeOut(bc, Expression.MODE_VALUE);
		wv.visitAfterExpressionBeforeBody(bc);
			getBody().writeOut(bc);
		wv.visitAfterBody(bc,getEnd());
	}


	/**
	 * @see lucee.transformer.bytecode.statement.FlowControl#getBreakLabel()
	 */
	public Label getBreakLabel() {
		return wv.getBreakLabel();
	}


	/**
	 * @see lucee.transformer.bytecode.statement.FlowControl#getContinueLabel()
	 */
	public Label getContinueLabel() {
		return wv.getContinueLabel();
	}


	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label=label;
	}

}
