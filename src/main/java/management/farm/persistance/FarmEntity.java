package management.farm.persistance;

import nonapi.io.github.classgraph.json.Id;

public record FarmEntity(@Id String id, String name, String description) {

}