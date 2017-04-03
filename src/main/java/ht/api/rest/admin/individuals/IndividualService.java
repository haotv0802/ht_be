package ht.api.rest.admin.individuals;

import ht.api.rest.admin.individuals.interfaces.IIndividualDao;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@Service("adminIndividualService")
public class IndividualService implements ht.api.rest.admin.individuals.interfaces.IIndividualService {

  private final IIndividualDao individualDao;

  @Autowired
  public IndividualService(@Qualifier("adminIndividualDao") IIndividualDao individualDao) {
    Assert.notNull(individualDao);
    this.individualDao = individualDao;
  }

  @Override
  public List<IndividualPresenter> getIndividuals() {
    List<IndividualModel> individuals = this.individualDao.getIndividuals();
    List<IndividualPresenter> individualList = new ArrayList<IndividualPresenter>();
    for (IndividualModel model : individuals) {
      IndividualPresenter presenter = new IndividualPresenter(model);
      individualList.add(presenter);
    }

    return individualList;
  }
}
