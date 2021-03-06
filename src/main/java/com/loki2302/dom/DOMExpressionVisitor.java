package com.loki2302.dom;

public interface DOMExpressionVisitor<T> {
	T visitDOMBinaryExpression(DOMBinaryExpression expression);
	T visitDOMLiteralExpression(DOMLiteralExpression expression);
	T visitDOMVariableReferenceExpression(DOMVariableReferenceExpression expression);
	T visitDOMArrayItemReferenceExpression(DOMArrayItemReferenceExpression expression);
}