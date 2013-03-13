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

public class ImplicitRightBinaryOperationMatcher implements BinaryOperationMatcher {	
	@Inject CastOperationRepository castOperationRepository;

	public ExpressionResult match(
			BinaryOperationRepository binaryOperationRepository,
			BinaryOperationSemantics semantics,
			Expression leftExpression, 
			Expression rightExpression) {
		Type leftType = leftExpression.getResultType();		
		BinaryOperationDefinition binaryOperationDefinition = 
				binaryOperationRepository.findByLeftType(
						semantics,
						leftType);
		
		if(binaryOperationDefinition != null) {
			Type requiredRightType = binaryOperationDefinition.getRightType();
			Type rightType = rightExpression.getResultType();
			
			CastOperationDefinition castOperationDefinition = 
					castOperationRepository.findImplicitBySourceAndDestinationTypes(
							rightType, 
							requiredRightType);
			
			if(castOperationDefinition != null) {
				return ExpressionResult.ok(binaryOperationDefinition.makeExpression(						
						leftExpression,
						castOperationDefinition.makeExpression(rightExpression)));
			}
		}
		
		return ExpressionResult.fail();
	}
}