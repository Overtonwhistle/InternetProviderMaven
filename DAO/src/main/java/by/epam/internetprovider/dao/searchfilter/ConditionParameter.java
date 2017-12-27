package by.epam.internetprovider.dao.searchfilter;

/**
 * Contains constants for specifying conditions in the compilation of search
 * queries
 * 
 * @author Pavel Sorokoletov
 */

public enum ConditionParameter {
	GREATER(" > "), LESS(" < "), EQUALS(" = ");
	String Condition;

	ConditionParameter(String condition) {
		this.Condition = condition;
	}

	public String getSqlCondition() {
		return Condition;
	}

}
