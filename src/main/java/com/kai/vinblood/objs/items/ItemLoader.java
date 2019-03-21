package com.kai.vinblood.objs.items;

import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.objs.entities.Entity;
import com.kai.vinblood.objs.entities.Projectile;
import com.kai.vinblood.objs.entities.enemies.load.EnemyLoader;
import com.kai.vinblood.objs.entities.player.skills.Skill;
import com.kai.vinblood.util.ID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kai on Mar 20, 2019
 */
public class ItemLoader {
    private ItemLoader() {}

    /*
    Item types:
        weapon
        rune
        rust

    Weapon stats:
        damage
        rate of attack

    player stats:
        swiftness
        defense
        bloodthirst
        max health
        mana
     */

    private static Map<ID, Item> items = new HashMap<>();

    public static void loadXMLS() throws ParserConfigurationException, IOException, SAXException {
        InputStream file = EnemyLoader.class.getResourceAsStream("/xmls/items.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringComments(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList itemNodeList = document.getElementsByTagName("Item");
        for (int i = 0; i < itemNodeList.getLength(); i++) {
            Element itemElement = (Element) itemNodeList.item(i);

            Item item = null;
            BufferedImage image;
            int damage = 0;
            double rateOfAttack = 0;
            List<ItemBehavior> itemBehaviors = new ArrayList<>();
            HashMap<ID, Projectile> projectiles = new HashMap<>();

            NodeList statNodeList = itemElement.getElementsByTagName("stat");
            for (int statNodeI = 0; statNodeI < statNodeList.getLength(); statNodeI++) {
                Element statElement = (Element) statNodeList.item(statNodeI);
                String stat = statElement.getAttribute("id");
                double amount = Double.valueOf(statElement.getTextContent());
                int intAmount = (int)(amount);
                if (!stat.equals("damage") && !stat.equals("rate of attack")) {
                    itemBehaviors.add(new ItemBehavior() {
                        public void onEquip(Entity owner) {
                            if (amount > 0) {
                                owner.statManager.incStat(stat, intAmount);
                            } else {
                                owner.statManager.decStat(stat, intAmount);
                            }
                        }

                        public void onUnEquip(Entity owner) {
                            if (amount > 0) {
                                owner.statManager.removeIncStat(stat, intAmount);
                            } else {
                                owner.statManager.removeDecStat(stat, intAmount);
                            }
                        }

                        public String getDescription() {
                            return ((intAmount > 0) ? "+" : "") + intAmount + " " + stat;
                        }
                    });
                } else {
                    if (stat.equals("damage")) {
                        damage = intAmount;
                    } else {
                        rateOfAttack = amount;
                    }
                }
            }


            NodeList keywordList = itemElement.getElementsByTagName("keyword");
            for (int keyWordI = 0; keyWordI < keywordList.getLength(); keyWordI++) {
                Element keywordElement = (Element) keywordList.item(keyWordI);
                String keyword = keywordElement.getTextContent();
                itemBehaviors.add(getSpecialBehavior(keyword));
            }

            NodeList projectileList = itemElement.getElementsByTagName("projectile");
            for (int projI = 0; projI < projectileList.getLength(); projI++) {
                Element projNode = (Element) projectileList.item(projI);
                Projectile p = EnemyLoader.getProjectile(projNode);
                ID projID = new ID(Integer.valueOf(projNode.getAttribute("id")));

                projectiles.put(projID, p);
            }


            ID itemID = new ID(Integer.valueOf(itemElement.getAttribute("id")), itemElement.getAttribute("name"));

            switch (itemElement.getAttribute("type")) {
                case "weapon":
                    image = ResourceManager.getImage("weapons", Integer.valueOf(itemElement.getAttribute("imagex")), Integer.valueOf(itemElement.getAttribute("imagey")), 8, 8);
                    item = new Weapon(image, itemBehaviors, projectiles, itemID);
                    ((Weapon) item).setDamage(damage);
                    ((Weapon) item).setRateOfAttack(rateOfAttack);
                    break;
                case "rune":
                    image = ResourceManager.getImage("runes", Integer.valueOf(itemElement.getAttribute("imagex")), Integer.valueOf(itemElement.getAttribute("imagey")), 8, 8);
                    Skill skill = new Skill(((Element)itemElement.getElementsByTagName("skill").item(0)).getAttribute("id"));
                    item = new Rune(image, itemBehaviors, skill, itemID);
                    break;
                case "rust":
                    image = ResourceManager.getImage("rusts", Integer.valueOf(itemElement.getAttribute("imagex")), Integer.valueOf(itemElement.getAttribute("imagey")), 8, 8);
                    item = new Rust(image, itemBehaviors, itemID);
                    break;
            }

            items.put(itemID, item);
        }

    }

    private static ItemBehavior getSpecialBehavior(String keyword) {
        return null;
    }

    public static Weapon getWeapon(ID id) {
        return (Weapon)items.get(id);
    }

    public static Rune getRune(ID id) {
        return (Rune)items.get(id);
    }

    public static Rust getRust(ID id) {
        return (Rust)items.get(id);
    }

}
