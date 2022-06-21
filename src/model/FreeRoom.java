package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return super.toString() + " Room Number " + roomNumber + " Price " + price +
                " Room Type " + enumeration;
    }
}