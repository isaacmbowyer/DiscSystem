package com.company;

/* This is my own data structure which allows the file to know what the user edited
    and update that specific record
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

    public Z getDisc() {
        return disc;
    }
}
