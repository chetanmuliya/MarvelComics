package cm.velotio.marvelcomics.mapper;

import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.model.CharactersResponse;
import cm.velotio.marvelcomics.model.ResultsItem;
import cm.velotio.marvelcomics.model.Thumbnail;

public class MapperManager {
    public ResultsItem map(CharacterEntity characterEntity) {
        ResultsItem resultsItem = new ResultsItem();
        resultsItem.setId(Integer.parseInt(characterEntity.id));
        resultsItem.setName(characterEntity.name);
        resultsItem.setThumbnail(new Thumbnail(characterEntity.thumbnail,characterEntity.extension));
        resultsItem.setName(characterEntity.thumbnail);
        return resultsItem;
    }

}
