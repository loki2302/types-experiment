package com.loki2302.evaluation;

import com.google.inject.Inject;
import com.loki2302.dom.DOMBinaryExpression;
import com.loki2302.dom.DOMExpression;
import com.loki2302.dom.DOMExpressionVisitor;
import com.loki2302.dom.DOMLiteralExpression;

public class DOMExpressionEvaluator {	
	@Inject DOMLiteralExpressionEvaluator literalExpressionEvaluator;
	@Inject DOMBinaryExpressionEvaluator binaryExpressionEvaluator;
	
	public ExpressionResult evaluateDOMExpression(DOMExpression domExpression) {
		return domExpression.accept(new DOMExpressionVisitor<ExpressionResult>() {
			@Override
			public ExpressionResult visitDOMBinaryExpression(DOMBinaryExpression expression) {
				return binaryExpressionEvaluator.evaluateDOMBinaryExpression(
						expression,
						DOMExpressionEvaluator.this);
			}

			@Override
			public ExpressionResult visitDOMLiteralExpression(DOMLiteralExpression expression) {
				return literalExpressionEvaluator.evaluateDOMLiteralExpression(expression);
			}			
		});
	}
}