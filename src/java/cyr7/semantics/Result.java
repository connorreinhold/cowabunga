package cyr7.semantics;

public class Result {
	private ResultOutcome outcome;
	private Context context;
	public Result(ResultOutcome outcome, Context updated) {
		this.outcome = outcome;
		this.context = updated;
	}
}
