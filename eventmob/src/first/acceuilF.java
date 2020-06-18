/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package first;
import Entite.Event;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.List;
import service.CrudEvent;

/**
 * The newsfeed form
 *
 * @author ASUS
 */
public class acceuilF extends Form {

    Form current;
    
    

    public acceuilF() {
        super("Newsfeed", BoxLayout.y());
        current = this;
        Resources res = UIManager.initNamedTheme("/theme", "Theme2");

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Produit");
        getContentPane().setScrollVisible(false);

        
       

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        Label spacer3 = new Label();
        Label spacer4 = new Label();
        //addTab(swipe, res.getImage("news-item.jpg"), spacer1, "15 Likes  ", "85 Comments", "Integer ut placerat purued non dignissim neque. ");

        addTab(swipe, res.getImage("1.jpg"), spacer1);
        addTab(swipe, res.getImage("2.jpg"), spacer3);
        addTab(swipe, res.getImage("3.jpg"), spacer4);
        addTab(swipe, res.getImage("4.jpg"), spacer2);
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Event", barGroup);
        all.setUIID("SelectBar");
      
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        CrudEvent ce = new CrudEvent();
        ArrayList<Event> liste = ce.getList2() ;
        for (Event e : liste) {
            String urlImage = e.getImage().substring(7);
            System.out.println("Image ::"+urlImage);
            int deviceWidth = 600;
            Image placeholder = Image.createImage(deviceWidth, deviceWidth, 0xbfc9d2);
            EncodedImage enc = EncodedImage.createFromImage(placeholder, false);
            Image im = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

            addButton(im, e);
            //addButton2(im, p.getNom());

        }
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }

        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addButton(Image img, Event event) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Label image = new Label(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(event.getNomEvent());
        ta.setUIID("NewsTopLine");
        ta.setSize(new Dimension(5, 5));
        ta.setEditable(false);
        Label p = new Label(event.getPrix() + " DT  ", "NewsBottomLine");
        Label m = new Label(event.getNb_p()+" Place disponible", "NewsTopLine");
        m.setTextPosition(RIGHT);

        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_SEND, "panierIcon", 4);
        FontImage icon2 = FontImage.createMaterial(FontImage.MATERIAL_ARROW_DOWNWARD, "panierIcon", 2);
        FontImage icon3 = FontImage.createMaterial(FontImage.MATERIAL_ARROW_UPWARD, "panierIcon", 2);
        Button participer = new Button(icon);
        participer.setUIID("labelPanier");
        Label l1 = new Label(icon2);
        Label l2 = new Label(icon3);
        cnt.setLeadComponent(participer);

        Container ccc1 = BoxLayout.encloseY(ta, m, p);
        Container ccc2 = BoxLayout.encloseY(participer);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseX(
                        ccc1
                ));
        cnt.add(BorderLayout.EAST,
                BoxLayout.encloseY(
                        l1,
                        ccc2,
                        l2
                ));
        add(cnt);
        // image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));

        participer.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ev) {
           }
        });
    }


    private void addButton2(Image img, String title) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);

        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);
        Button test = new Button();
        cnt.setLeadComponent(test);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta, test
                )
        );
        add(cnt);

        test.addActionListener(e -> {
            System.out.println("Title : " + title);
        });
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
