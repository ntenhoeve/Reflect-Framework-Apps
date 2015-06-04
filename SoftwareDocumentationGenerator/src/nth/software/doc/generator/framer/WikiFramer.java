package nth.software.doc.generator.framer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import nth.software.doc.generator.model.Bold;
import nth.software.doc.generator.model.Chapter;
import nth.software.doc.generator.model.DocumentationModel;
import nth.software.doc.generator.model.Hyperlink;
import nth.software.doc.generator.model.Image;
import nth.software.doc.generator.model.LineBreak;
import nth.software.doc.generator.model.List;
import nth.software.doc.generator.model.ListItem;
import nth.software.doc.generator.model.Node;
import nth.software.doc.generator.model.Paragraph;
import nth.software.doc.generator.model.SubParagraph;
import nth.software.doc.generator.model.TextNode;
import nth.software.doc.generator.model.Underline;
import nth.software.doc.generator.model.inlinetag.InlineTag;
import nth.software.doc.generator.service.DocumentationInfo;
import nth.software.doc.generator.tokenizer.ElementName;

public class WikiFramer extends HtmlSingleFileFramer {

	
	public WikiFramer(DocumentationModel documentationModel,
			DocumentationInfo htmlInfo, File destinationFolder)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(documentationModel, htmlInfo, destinationFolder);
	}

	@Override
	public void onStartFraming() {
		try {
			createNewFile("Home.md");
			//writeImage(); TODO does not work in home ????
			writeTableOfContents();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	@Override
	public String createChapterLink(Chapter chapterOrParagraphOrSubParagraph) {
		StringBuilder link = new StringBuilder();
		Chapter chapter=documentationModel.findChapter(chapterOrParagraphOrSubParagraph);
		java.util.List<Chapter> chapters=documentationModel.findChapters();
		int chapterNr = chapters.indexOf(chapter)+1;
		if (chapterNr<10) {
			link.append("0");
		}
		link.append(chapterNr);
		link.append("-");
		link.append(chapter.getTitle().replace(" ", "-"));
		if (chapterOrParagraphOrSubParagraph instanceof Paragraph) {
			link.append("#");
			link.append(chapterOrParagraphOrSubParagraph.getTitle().toLowerCase().replace(" ", "-"));
		}
		return link.toString();
	}

	

	@Override
	public void frameChapter(Chapter chapter) {
		String fileName = createChapterFileName(chapter);
		try {
			createNewFile(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		super.frameChapter(chapter);
		writeFooter(chapter);
	}

	private void writeFooter(Chapter chapter) {
		PrintWriter writer = getWriter();
		writer.print("\r\n\r\n---\r\n");
		
		java.util.List<Chapter> chapters = documentationModel.findChapters();
		int chapterIndex = chapters.indexOf(chapter);
		
		if (chapterIndex>0) {
			writePreviousPageLink(chapters, chapterIndex);
		}
		writeHomePageLink();

		if (chapterIndex<chapters.size()-1) {
			writeNextPageLink(chapters, chapterIndex);
		}
	}


	private void writeHomePageLink() {
		String text="<Home>";
		String link= "Home";
		Hyperlink hyperlink=new Hyperlink(text, link);
		frameHyperlink(hyperlink);
		writeSpacer();
	}

	private void writeNextPageLink(java.util.List<Chapter> chapters,
			int chapterIndex) {
		Chapter nextChapter = chapters.get(chapterIndex+1);
		String text="Next>";
		String link= createChapterLink(nextChapter);
		Hyperlink hyperlink=new Hyperlink(text, link);
		frameHyperlink(hyperlink);
	}

	private void writePreviousPageLink(java.util.List<Chapter> chapters,
			int chapterIndex) {
		Chapter previousChapter = chapters.get(chapterIndex-1);
		String text="<Previous";
		String link= createChapterLink(previousChapter);
		Hyperlink hyperlink=new Hyperlink(text, link);
		frameHyperlink(hyperlink);
		writeSpacer();
	}

	private void writeSpacer() {
		PrintWriter writer=getWriter();
		writer.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}

	private String createChapterFileName(Chapter chapter) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(createChapterLink(chapter));
		fileName.append(".md");
		return fileName.toString();
	}


	
}
