package com.loki2302.evaluation.matcher;

import com.google.inject.Inject;
import com.loki2302.evaluation.ExpressionResult;
import com.loki2302.evaluation.operations.BinaryOperationDefinition;
import com.loki2302.evaluation.operations.BinaryOperationRepository;
import com.loki2302.evaluation.operations.cast.CastOperationDefinition;
import com.loki2302.evaluation.operations.cast.CastOperationRepository;
import com.loki2302.expression.BinaryOperationSemantics;
import com.loki2302.expression.Expression;
import com.loki2302.expression.Type;

public class ImplicitLeftBinaryOperationMatcher implements BinaryOperationMatcher {
	@Inject CastOperationRepository castOperationRepository;
	
	public ExpressionResult match(
			BinaryOperationRepository binaryOperationRepository,
			BinaryOperationSemantics semantics,
			Expression leftExpression, 
			Expression rightExpression) {
		
		Type rightType = rightExpression.getResultType();		
		BinaryOperationDefinition binaryOperationDefinition = 
				binaryOperationRepository.findByRightType(
						semantics, 
						rightType);
		
		if(binaryOperationDefinition != null) {
			Type requiredLeftType = binaryOperationDefinition.getLeftType();
			Type leftType = leftExpression.getResultType();
			
			CastOperationDefinition castOperationDefinition = 
					castOperationRepository.findImplicitBySourceAndDestinationTypes(
							leftType, 
							requiredLeftType);
			
			if(castOperationDefinition != null) {
				return ExpressionResult.ok(binaryOperationDefinition.makeExpression(
						castOperationDefinition.makeExpression(leftExpression),
						rightExpression));
			}
		}
		
		return ExpressionResult.fail();
	}	
}