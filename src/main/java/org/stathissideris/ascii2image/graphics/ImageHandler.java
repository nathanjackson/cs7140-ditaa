/**
 * ditaa - Diagrams Through Ascii Art
 * 
 * Copyright (C) 2004-2011 Efstathios Sideris
 *
 * ditaa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * ditaa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with ditaa.  If not, see <http://www.gnu.org/licenses/>.
 *   
 */
package org.stathissideris.ascii2image.graphics;

import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.util.DocumentFactory;
import org.apache.batik.ext.awt.image.codec.png.PNGEncodeParam;
import org.apache.batik.ext.awt.image.codec.png.PNGImageEncoder;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.renderer.ConcreteImageRendererFactory;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.gvt.renderer.ImageRendererFactory;
import org.apache.batik.gvt.renderer.StaticRenderer;
import org.stathissideris.ascii2image.core.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;

public class ImageHandler {
	
	private static OffScreenSVGRenderer svgRenderer = 
		new OffScreenSVGRenderer();
	
	private static ImageHandler instance = new ImageHandler();
	
	public static ImageHandler instance(){
		return instance;
	}
	
	private static final MediaTracker tracker = new MediaTracker(new JLabel());
	
	public BufferedImage loadBufferedImage(File file) throws IOException {
		return ImageIO.read(file);
	}
	
	public Image loadImage(String filename){
		URL url = ClassLoader.getSystemResource(filename);
		Image result = null;
		if(url != null)
			result = Toolkit.getDefaultToolkit().getImage(url);
		else
			result = Toolkit.getDefaultToolkit().getImage(filename);
//			result = null;

		//wait for the image to load before returning
		tracker.addImage(result, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			System.err.println("Failed to load image "+filename);
			e.printStackTrace();
		}
		tracker.removeImage(result, 0);
		
		return result;
	}
	
	public BufferedImage renderSVG(String filename, int width, int height, boolean stretch) throws IOException {
		File file = new File(filename);
		URI uri = file.toURI();
		return svgRenderer.renderToImage(uri.toString(), width, height, stretch, null, null);
	}

	public BufferedImage renderSVG(String filename, int width, int height, boolean stretch, String idRegex, Color color) throws IOException {
		File file = new File(filename);
		URI uri = file.toURI();
		return svgRenderer.renderToImage(uri.toString(), width, height, stretch, idRegex, color);
	}


}
