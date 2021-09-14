package de.btu.swp.carcassonne.util;

import org.controlsfx.glyphfont.Glyph;

public class GlyphUtil {
	
	private GlyphUtil() {}
	
	public final static double FONT_SIZE = 20;
	public final static double FONT_SIZE_SMALL = 12;
	
	public final static Glyph up() {
		Glyph glyph = new Glyph("FontAwesome","CARET_UP");
		glyph.setFontSize(FONT_SIZE_SMALL);
		return glyph;
	}
	
	public final static Glyph down() {
		Glyph glyph = new Glyph("FontAwesome","CARET_DOWN");
		glyph.setFontSize(FONT_SIZE_SMALL);
		return glyph;
	}
	
	public final static Glyph rotateRight() {
		Glyph glyph = new Glyph("FontAwesome","REPEAT");
		glyph.setFontSize(FONT_SIZE);
		return glyph;
	}
	
	public final static Glyph rotateLeft() {
		Glyph glyph = new Glyph("FontAwesome","UNDO");
		glyph.setFontSize(FONT_SIZE);
		return glyph;
	}
	
	public final static Glyph menu() {
		Glyph glyph = new Glyph("FontAwesome","BARS");
		glyph.setFontSize(FONT_SIZE);
		return glyph;
	}

	public final static Glyph settings() {
		Glyph glyph = new Glyph("FontAwesome","GEARS");
		glyph.setFontSize(FONT_SIZE);
		return glyph;
	}

}
