package com.example.domain;

import com.example.PetRepositoryApi;
import com.google.protobuf.Empty;
import kalix.javasdk.valueentity.ValueEntity.Effect;
import kalix.javasdk.valueentity.ValueEntityContext;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
// This is the implementation for the Value Entity Service described in your com/example/api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class PetCatalogEntity extends AbstractPetCatalogEntity {
  @SuppressWarnings("unused")
  private final String entityId;

  public PetCatalogEntity(ValueEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public PetItemDomain.Pet emptyState() {
    return PetItemDomain.Pet.getDefaultInstance();
  }

  @Override
  public Effect<Empty> add(PetItemDomain.Pet currentState, PetRepositoryApi.PetItem petItem) {
    PetItemDomain.Pet newPet = currentState.toBuilder()
            .setName(petItem.getName())
            .setImage(petItem.getImage())
            .setCount(1).build();
    return effects()
            .updateState(newPet)
            .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<PetRepositoryApi.PetItem> getDetails(PetItemDomain.Pet currentState, PetRepositoryApi.PetItemId petItemId) {
    PetRepositoryApi.PetItem petItem = PetRepositoryApi.PetItem.newBuilder()
            .setImage(currentState.getImage())
            .setName(currentState.getName())
            .build();
    return effects().reply(petItem);
  }
}
