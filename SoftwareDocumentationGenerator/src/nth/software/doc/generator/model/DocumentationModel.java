package nth.software.doc.generator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nth.software.doc.generator.model.inlinetag.InlineTag;
import nth.software.doc.generator.tokenizer.ElementName;
import nth.software.doc.generator.tokenizer.InlineTagName;

public class DocumentationModel extends NodeContainer<Node> {

	private final File javaProjectsFolder;

	public DocumentationModel(File javaProjectsFolder, List<Node> nodes) {
		super();
		this.javaProjectsFolder = javaProjectsFolder;
		getChildren().addAll(nodes);
	}

	public Node findChapterOrParagraphWithBeginOfFileTag(String tagName) {
		for (Node node : getChildren()) {
			if (node instanceof Chapter) {
				Chapter chapter = (Chapter) node;
				if (chapter.containsBeginOfFileTag(tagName)) {
					return chapter;
				}

				for (Node child : chapter.getChildren()) {
					if (child instanceof Paragraph) {
						Paragraph paragraph = (Paragraph) child;
						if (paragraph.containsBeginOfFileTag(tagName)) {
							return paragraph;
						}
					}
				}
			}
		}
		return null;
	}

	public File getJavaProjectsFolder() {
		return javaProjectsFolder;
	}

	public InlineTag findBeginOffFileInParent(Node node) {
		NodeContainer<Node> parent = null;
		NodeContainer<Node> currentNode = this;
		InlineTag beginOfFile = null;
		do {
			parent = findParent(currentNode, node);
			beginOfFile = findBeginOffFile(parent);
			if (beginOfFile==null) {
				node=parent;
			}
		} while (beginOfFile == null && parent != this);
		return beginOfFile;
	}

	public InlineTag findBeginOffFile(NodeContainer<Node> container) {
		for (Node child :container.getChildren()) {
			if (child instanceof InlineTag) {
				InlineTag inlineTag = (InlineTag) child;
				if (inlineTag.getName()==InlineTagName.BEGINOFFILE) {
					return inlineTag;
				}
			}
		}
		//not found;
		return null;
	}

	private NodeContainer<Node> findParent(NodeContainer<Node> parent,
			Node nodeToFind) {
		for (Node child : parent.getChildren()) {
			if (child == nodeToFind) {
				return parent;
			}
		}
		// try one level deeper
		for (Node child : parent.getChildren()) {
			if (child instanceof NodeContainer) {
				NodeContainer<Node> deeper_parent = (NodeContainer<Node>) child;
				NodeContainer<Node> foundParent = findParent(deeper_parent,
						nodeToFind);
				if (foundParent != null) {
					return foundParent;
				}
			}
		}
		// not found
		return null;
	}

	public Chapter findChapter(Chapter chapterOrParagraphOrSubParagraph) {
		if (isChapter(chapterOrParagraphOrSubParagraph) ){
			return chapterOrParagraphOrSubParagraph;
		}
		NodeContainer<Node> parent = findParent(this, chapterOrParagraphOrSubParagraph);
		if (isChapter(parent) ){
			return (Chapter) parent;
		}
		parent = findParent(this,  parent);
		if (isChapter(parent) ){
			return (Chapter) parent;
		}
		return null;
	}

	private boolean isChapter(Node node) {
		return node instanceof Chapter && !(node instanceof Paragraph) && !(node instanceof SubParagraph);
	}

	public List<Chapter> findChapters() {
		List<Chapter> chapters=new ArrayList<Chapter>();
		for (Node child : getChildren()) {
			if (child instanceof Chapter) {
				Chapter chapter=(Chapter) child;
				chapters.add(chapter);
			}
		}
		return chapters;
	}

	public int getChapterNumber(Chapter chapter) {
		List<Chapter> chapters = findChapters();
		int chapterNumber = chapters.indexOf(chapter)+1;
		return chapterNumber;
	}

}