package com.loki2302.dom;

public class DOMVariableReferenceExpression implements DOMExpression {
	private final String variableName;
	
	public DOMVariableReferenceExpression(String variableName) {
		this.variableName = variableName;
	}
	
	public String getVariableName() {
		return variableName;
	}
	
	@Override
	public <T> T accept(DOMExpressionVisitor<T> visitor) {
		return visitor.visitDOMVariableReferenceExpression(this);
	}
}