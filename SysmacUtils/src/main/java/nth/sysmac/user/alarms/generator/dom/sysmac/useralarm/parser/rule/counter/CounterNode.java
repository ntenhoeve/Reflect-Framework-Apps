package nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.parser.rule.counter;

import nth.reflect.util.parser.node.Node;
import nth.reflect.util.parser.node.text.TextProvider;
import nth.sysmac.user.alarms.generator.dom.sysmac.basetype.GoToNextListener;

/**
 * @see {@link CounterRule}.
 * @author nilsth
 *
 */
public class CounterNode extends Node implements GoToNextListener, TextProvider {

	/**
	 * &lt;array=array number&gt; is optional:
	 * <ul>
	 * <li>no array attribute or array=0: counter increases when any array is
	 * increased and will not reset within the array</li>
	 * <li>array=1: counter increases when the last array is increased, the counter
	 * is reset if the second last array is increased</li>
	 * <li>array=2: counter increases when the second last array is increased, the
	 * counter is reset if the third last array is increased</li>
	 * <li>etcetera</li>
	 * </ul>
	 * 
	 */

	private int array = 0;
	private int counterValue = 0;

	public CounterNode(int array) {
		this.array = array;
	}

	@Override
	public void goToNext(int arrayThatHasIncreased) {
		if (array == arrayThatHasIncreased) {
			counterValue++;
		}
		if (array != 0 && (array + 1) == arrayThatHasIncreased) {
			counterValue = 0;
		}
		// TODO skip rules
	}

	@Override
	public String getText() {
		return Integer.toString(counterValue);
	}

//	public CounterNode(List<SkipRules> skipRules) {
//		super( skipColumns);
//	}

}
