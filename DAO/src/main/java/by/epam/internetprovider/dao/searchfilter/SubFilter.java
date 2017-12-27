/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.searchfilter;

/**
 * Object containing the parameter, condition, and value of the search
 * sub-filter. List of this objects may be processed by the {@link SearchFilter}
 * to get a composite search query.
 * 
 * @author Pavel Sorokoletov
 */

public class SubFilter {

	private static final String EMPTY_VALUE = "empty_value";
	private static final String S_QUOTE = "'";

	/** The name of parameter. Always present in SubFilter. */
	public FilterParameter name;

	/** The condition of expression. Optional. */
	public ConditionParameter condition;

	/** The value of expression. Optional. */
	public String value;

	public SubFilter(FilterParameter parameterName, String condition, String parameterValue) {
		super();
		this.name = parameterName;
		this.condition = ConditionParameter.valueOf(condition.toUpperCase());
		this.value = S_QUOTE + parameterValue + S_QUOTE;
	}

	public SubFilter(FilterParameter parameterName, String condition, int parameterValue) {
		super();
		this.name = parameterName;
		this.condition = ConditionParameter.valueOf(condition.toUpperCase());
		this.value = S_QUOTE + Integer.toString(parameterValue) + S_QUOTE;
	}

	public SubFilter(FilterParameter parameterName, String parameterValue) {
		super();
		this.name = parameterName;
		this.condition = ConditionParameter.EQUALS;
		this.value = S_QUOTE + parameterValue + S_QUOTE;
	}

	public SubFilter(FilterParameter parameterName, int parameterValue) {
		super();
		this.name = parameterName;
		this.condition = ConditionParameter.EQUALS;
		this.value = S_QUOTE + Integer.toString(parameterValue) + S_QUOTE;
	}

	public SubFilter(FilterParameter parameterName) {
		super();
		this.name = parameterName;
		this.condition = ConditionParameter.EQUALS;
		this.value = EMPTY_VALUE;
	}

}
