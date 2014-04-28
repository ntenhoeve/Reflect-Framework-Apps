package nth.github.page.generator.wiki.model;

import java.util.List;

import nth.github.page.generator.Config;
import nth.github.page.generator.model.html.element.page.content.PathFactory;
import nth.github.page.generator.model.text.Node;
import nth.github.page.generator.model.text.TextChapterLevel1;
import nth.github.page.generator.model.text.TextChapterLevel2;
import nth.github.page.generator.model.text.TextDocument;

public class WikiPageHome extends WikiPage {

	private final List<Node> chapters;

	public WikiPageHome(Config config, List<Node> chapters) {
		super(config);
		this.chapters = chapters;
	}

	@Override
	public void addContent() {
		for (Node node : chapters) {
			TextChapterLevel1 textChapterLevel1 = (TextChapterLevel1) node;
			getChilderen().add(new WikiText("\n### "));//add text so that chapter has H3 style
			getChilderen().add(
					new WikiHyperlink(textChapterLevel1.getTitle(), PathFactory
							.createRemoteGitHubWikiPath(getConfig(), textChapterLevel1,
									null)));
			getChilderen().add(new WikiText("\n"));//end of H3 style
			
			List<Node> subChapters = textChapterLevel1.findChilderenOfType(TextChapterLevel2.class);
			for (Node subChapter: subChapters) {
				TextChapterLevel2 textChapterLevel2=(TextChapterLevel2)subChapter;
				getChilderen().add(
						new WikiHyperlink(textChapterLevel2.getTitle(), PathFactory
								.createRemoteGitHubWikiPath(getConfig(), textChapterLevel1,
										textChapterLevel2)));
				getChilderen().add(new WikiText("<br>"));//new line
			}
			
		}

	}

	@Override
	public String getPath() {
		return PathFactory.createRemoteGitHubRepositoryWikiUri(getConfig()).toString();
	}

	@Override
	public String getTitle() {
		return "Home";
	}

}
