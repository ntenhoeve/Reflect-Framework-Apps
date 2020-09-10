package nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node;

import java.util.function.Predicate;

import com.google.common.base.Optional;

import nth.reflect.util.regex.Regex;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.token.Rest;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.token.TokenRule;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.token.rule.TokenRules;

public class TokenNodePredicate implements Predicate<Node> {

	private final TokenRule ruleToFind;
	private final Optional<Regex> regex;

	public TokenNodePredicate(TokenRule ruleToFind, Optional<Regex> regex) {
		this.ruleToFind = ruleToFind;
		this.regex = regex;
	}

	public TokenNodePredicate(TokenRule tokenRuleToFind) {
		this(tokenRuleToFind, Optional.absent());
	}

	@Override
	public boolean test(Node node) {
		if (!(node instanceof TokenNode)) {
			return false;
		}
		TokenNode tokenNode = (TokenNode) node;
		TokenRule tokenRule = tokenNode.getRule();
		boolean identicalDataTypes = tokenRule.getClass() == ruleToFind.getClass();
		if (!identicalDataTypes) {
			return false;
		}

		if (regex.isPresent()) {
			boolean matches = regex.get().hasMatchIn(tokenNode.getValue());
			return matches;
		} else {
			return true;
		}
	}

	public static TokenNodePredicate openBrace() {
		return new TokenNodePredicate(TokenRules.OPEN_BRACE.get());
	}

	public static TokenNodePredicate closeBrace() {
		return new TokenNodePredicate(TokenRules.CLOSE_BRACE.get());
	}

	public static TokenNodePredicate whiteSpace() {
		return new TokenNodePredicate(TokenRules.WHITESPACE.get());
	}

	public static TokenNodePredicate rest(Regex regex) {
		return new TokenNodePredicate(new Rest(), Optional.of(regex));
	}

}
