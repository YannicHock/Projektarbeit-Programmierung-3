package de.prog3.projektarbeit.data;

public enum Position {
    GK("Torwart"),
    RB("Rechter Verteidiger"),
    LB("Linker Verteidiger"),
    CB("Innenverteidiger"),
    CDM("Zentraler Defensiver Mittelfeldspieler"),
    RM("Rechter Mittelfeldspieler"),
    LM("Linker Mittelfeldspieler"),
    CM("Zentraler Mittelfeldspieler"),
    CAM("Zentraler Offensiver Mittelfeldspieler"),
    ST("Stürmer"),
    RW("Rechter Flügelspieler"),
    LW("Linker Flügelspieler"),
    RS("Rechter Stürmer"),
    LS("Linker Stürmer")
    ;

    final String friendlyName;
    Position(String friendlyName){
        this.friendlyName = friendlyName;
    }

    public static Position getByFriendlyName(String friendlyName){
        for(Position position : Position.values()){
            if(position.getFriendlyName().equals(friendlyName)){
                return position;
            }
        }
        return null;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}