package ht.api.rest.admin.individuals.interfaces;

import ht.api.rest.admin.individuals.IndividualModel;

import java.util.List;

/**
 * Created by haho on 4/3/2017.
 */
public interface IIndividualDao {
  List<IndividualModel> getIndividuals();
}
