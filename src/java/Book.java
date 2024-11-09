package wit;

import java.nio.ByteBuffer;

public class Book {
    private String isbn;
    private String name;
    private int quantity;
    private String pubish;
    private int price;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPubish() {
        return pubish;
    }

    public void setPubish(String pubish) {
        this.pubish = pubish;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
