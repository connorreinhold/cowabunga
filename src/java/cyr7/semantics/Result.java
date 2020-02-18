package cyr7.semantics;

public class Result {

	public final ResultType outcome;
	public final Context context;

	public Result(ResultType outcome, Context updated) {
		this.outcome = outcome;
		this.context = updated;
	}

}
