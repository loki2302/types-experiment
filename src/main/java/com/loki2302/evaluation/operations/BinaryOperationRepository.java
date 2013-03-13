package com.loki2302.evaluation.operations;

import java.util.List;

import com.loki2302.expression.BinaryOperationFamily;
import com.loki2302.expression.Type;

public class BinaryOperationRepository {
	private final List<BinaryOperationDefinition> definitions;
	
	public BinaryOperationRepository(List<BinaryOperationDefinition> definitions) {
		this.definitions = definitions;
	}
	
	public BinaryOperationDefinition firstWhere(BinaryOperationDefinitionPredicate... predicates) {
		for(BinaryOperationDefinition operationDefinition : definitions) {
			boolean found = true;
			for(BinaryOperationDefinitionPredicate predicate : predicates) {
				if(!predicate.match(operationDefinition)) {
					found = false;
					break;
				}
			}
			
			if(found) {
				return operationDefinition;
			}
		}
		
		return null;
	}

	public static BinaryOperationDefinitionPredicate familyIs(BinaryOperationFamily family) {
		return new FamilyIsPredicate(family);
	}
	
	public static BinaryOperationDefinitionPredicate leftTypeIs(Type leftType) {
		return new LeftTypeIsPredicate(leftType);
	}
	
	public static BinaryOperationDefinitionPredicate rightTypeIs(Type rightType) {
		return new RightTypeIsPredicate(rightType);
	}
	
	public static interface BinaryOperationDefinitionPredicate {
		boolean match(BinaryOperationDefinition item);
	}
	
	private static class FamilyIsPredicate implements BinaryOperationDefinitionPredicate {
		private final BinaryOperationFamily family;
		
		public FamilyIsPredicate(BinaryOperationFamily family) {
			this.family = family;
		}
		
		@Override
		public boolean match(BinaryOperationDefinition item) {
			return item.getBinaryOperationFamily() == family;
		}		
	}
	
	private static class LeftTypeIsPredicate implements BinaryOperationDefinitionPredicate {
		private final Type type;
		
		public LeftTypeIsPredicate(Type type) {
			this.type = type;
		}
		
		@Override
		public boolean match(BinaryOperationDefinition item) {
			return item.getLeftType() == type;
		}		
	}
	
	private static class RightTypeIsPredicate implements BinaryOperationDefinitionPredicate {
		private final Type type;
		
		public RightTypeIsPredicate(Type type) {
			this.type = type;
		}
		
		@Override
		public boolean match(BinaryOperationDefinition item) {
			return item.getRightType() == type;
		}		
	}
}