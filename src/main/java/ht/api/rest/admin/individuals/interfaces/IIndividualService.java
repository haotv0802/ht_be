package ht.api.rest.admin.individuals.interfaces;

import ht.api.rest.admin.individuals.IndividualPresenter;

import java.util.List;

/**
 * Created by haho on 4/3/2017.
 */
public interface IIndividualService {
  List<IndividualPresenter> getIndividuals();

  Boolean isUserNameExisting(String username);

  Boolean isUserNameExisting(String oldUserName, String userName);
}
