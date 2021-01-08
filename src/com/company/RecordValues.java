package com.company;

/* This is my own data structure which allows the CSV file to know what the user edited
    and update that specific record in the CSV file -> otherwise it will have no idea as it needs ALL 3 of these values
 */

public class RecordValues<T extends String, Y extends String, Z extends Disc> {
    private T oldTitle;
    private Y editOption;
    private Z disc;

    RecordValues(T oldTitle, Y editOption, Z disc){
      this.oldTitle = oldTitle;
      this.editOption = editOption;
      this.disc = disc;
    }

    public T getOldTitle() {
        return oldTitle;
    }
    public Y getEditOption(){
        return editOption;
    }

    public Z getDisc() { return disc; }
}
