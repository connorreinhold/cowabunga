package cyr7.semantics;

public class Result {
	private ResultType outcome;
	private Context context;
	public Result(ResultType outcome, Context updated) {
		this.outcome = outcome;
		this.context = updated;
	}
}
