package nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.rule;

import java.util.List;

import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.Node;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.NodePredicate;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.NodeRule;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.TokenNodePredicate;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.matcher.NodeMatcher;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.matcher.result.Results;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.matcher.result.filter.GroupNameResultFilter;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.matcher.rule.Repetition;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.matcher.rule.Rules;

public class BraceRule implements NodeRule<BraceNode> {

	private static final String CLOSE_BRACE = "closeBrace";
	private static final String OPEN_BRACE = "openBrace";
	private static final String BETWEEN_BRACES = "betweenBraces";

	@Override
	public Results find(List<Node> nodes) {
		Rules rules = new Rules()//
				.newGroup(OPEN_BRACE)//
				.node(TokenNodePredicate.openBrace())//
				.newGroup(BETWEEN_BRACES)//
				.node(NodePredicate.any(), Repetition.zeroOrMore())//
				.newGroup(CLOSE_BRACE)//
				.node(TokenNodePredicate.closeBrace());

		NodeMatcher nodeMatcher = new NodeMatcher(rules);
		Results results = nodeMatcher.match(nodes);
		return results;
	}

	@Override
	public BraceNode createReplacement(Results results) {
		GroupNameResultFilter groupNameFilter = new GroupNameResultFilter(BETWEEN_BRACES);
		List<Node> childrenBetweenBrackets = results.getChildren(groupNameFilter);
		BraceNode braceNode = new BraceNode(childrenBetweenBrackets);
		return braceNode;
	}
}
