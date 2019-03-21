package com.kai.vinblood.objs.entities.enemies.load;

import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.objs.entities.Projectile;
import com.kai.vinblood.objs.entities.enemies.Enemy;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.ID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EnemyLoader {
    private EnemyLoader() {}

    /*
    Stats:
        max health
        mana
        swiftness
        bloodthirst
        defense
        damage
        rate of attack
     */

    private static Map<ID, Enemy> enemies = new HashMap<>();

    public static void loadXMLS() throws ParserConfigurationException, IOException, SAXException {
        InputStream file = EnemyLoader.class.getResourceAsStream("/xmls/enemies.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setValidating(false);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList nlist = document.getElementsByTagName("Enemy");
        for (int i = 0; i < nlist.getLength(); i++) {
            Node n = nlist.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                ID id = new ID(Integer.valueOf(e.getAttribute("id")), e.getAttribute("name"));

                BufferedImage image = ResourceManager.getImage(e.getAttribute("image"), Integer.valueOf(e.getAttribute("width")), Integer.valueOf(e.getAttribute("height")));
                Enemy enemy = new Enemy(image);


                enemy.setBounds(new Bounds(0, 0, Integer.valueOf(e.getAttribute("width")), Integer.valueOf(e.getAttribute("height"))));

                NodeList statlist = e.getElementsByTagName("stat");
                for (int st = 0; st < statlist.getLength(); st++) {
                    Element statNode = (Element) statlist.item(st);

                    enemy.statManager.setStat(statNode.getAttribute("id"), Integer.valueOf(statNode.getTextContent()));
                    if (statNode.getAttribute("id").equals("max health")) {
                        enemy.statManager.setStat("health", Integer.valueOf(statNode.getTextContent()));
                    }
                }

                NodeList projlist = e.getElementsByTagName("projectile");
                for (int prj = 0; prj < projlist.getLength(); prj++) {
                    Element projNode = (Element) projlist.item(prj);

                    Projectile p = getProjectile(projNode);
                    ID projID = new ID(Integer.valueOf(projNode.getAttribute("id")));

                    enemy.getProjectiles().put(projID, p);
                }

                NodeList dropList = e.getElementsByTagName("drop");
                for (int di = 0; di < dropList.getLength(); di++) {
                    Element dropElement = (Element) dropList.item(di);

                    ID itemID = new ID(Integer.valueOf(dropElement.getAttribute("id")), dropElement.getAttribute("name"));
                    double frequency = Double.valueOf(dropElement.getAttribute("rate"));
                    enemy.getDrops().put(itemID, frequency);
                }

                enemy.id = new ID(id.idnum, id.idname);

                //TODO: Add drops.

                enemies.put(id, enemy);
            }
        }
    }


    public static Projectile getProjectile(Element projNode) {
        BufferedImage projImage = ResourceManager.getImage("projectiles", Integer.valueOf(projNode.getAttribute("imagex")), Integer.valueOf(projNode.getAttribute("imagey")), 8, 8);
        projImage = ResourceManager.toBufferedImage(projImage.getScaledInstance(Integer.valueOf(projNode.getAttribute("width")), Integer.valueOf(projNode.getAttribute("height")), Image.SCALE_FAST));
        Projectile p = new Projectile(projImage);
        if (projNode.hasAttribute("variance")) {
            p.setVariance(Integer.valueOf(projNode.getAttribute("variance")));
        }
        if (projNode.hasAttribute("speed")) {
            p.setSpeed(Integer.valueOf(projNode.getAttribute("speed")));
        }
        if (projNode.hasAttribute("damage")) {
            p.setDamage(Integer.valueOf(projNode.getAttribute("damage")));
        }
        if (projNode.hasAttribute("range")) {
            p.setRange(Integer.valueOf(projNode.getAttribute("range")));
        }

        p.setBounds(new Bounds(0, 0, Integer.valueOf(projNode.getAttribute("width")), Integer.valueOf(projNode.getAttribute("height"))));

        return p;
    }

    public static Enemy getEnemy(ID id) {
        return enemies.get(id);
    }



}
