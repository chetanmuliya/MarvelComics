package cm.velotio.marvelcomics.database.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "characters")
public class CharacterEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String id;
    public String name;
    public String thumbnail;
    public String extension;
    public Boolean isBookmarked = false;

    public CharacterEntity(String id, String name, String thumbnail, String extension) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.extension = extension;
    }
}
