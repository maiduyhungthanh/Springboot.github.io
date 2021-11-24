package techmaster.lesson8.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import techmaster.lesson8.model.StaffModel;
import techmaster.lesson8.repository.Dao;
import techmaster.lesson8.repository.StaffDao;
import techmaster.lesson8.model.StaffEntity;

@Service
public class StaffService implements iService<StaffModel> {
        @Autowired
        Dao<StaffEntity> staffDao;

        private List<StaffModel> listMappingEntityToModel(List<StaffEntity> list) {
                List<StaffModel> returnList = list.stream()
                                .map(staffEntity -> StaffModel.builder()
                                                .setId(staffEntity.getId())
                                                .setFirstName(staffEntity.getFirstName())
                                                .setLastName(staffEntity.getLastName())
                                                .setEmail(staffEntity.getEmail())
                                                .setPassport(staffEntity.getPassport())
                                                .setAvatar(staffEntity.getAvatar())
                                                .build())
                                .collect(Collectors.toList());
                returnList.forEach(item -> item.setFullName());
                return returnList;
        }

        private StaffModel objectMappingEntityToModel(StaffEntity entity) {
                StaffModel staffModel = StaffModel.builder()
                                .setId(entity.getId())
                                .setFirstName(entity.getFirstName())
                                .setLastName(entity.getLastName())
                                .setEmail(entity.getEmail())
                                .setPassport(entity.getPassport())
                                .setAvatar(entity.getAvatar())
                                .build();
                staffModel.setFullName();
                return staffModel;
        }

        private StaffEntity objectMappingModelToEntity(StaffModel model) {
                StaffEntity staffEntity = StaffEntity.builder()
                                .setId(model.getId())
                                .setFirstName(model.getFirstName())
                                .setLastName(model.getLastName())
                                .setEmail(model.getEmail())
                                .setPassport(model.getPassport())
                                .setAvatar(model.getAvatar())
                                .build();
                return staffEntity;
        }

        @Override
        public List<StaffModel> getList() {
                return listMappingEntityToModel(staffDao.list());
        }

        @Override
        public List<StaffModel> getList(Integer limit, Integer offset) {
                return listMappingEntityToModel(staffDao.list(limit, offset));
        }


        @Override
        public StaffModel getByID(Long id) throws DataAccessException {
                StaffModel staffModel = objectMappingEntityToModel(staffDao.getID(id));
                if (staffModel.getAvatar() != null) {
                        staffModel.setAvatarBase64(Base64.getEncoder().encodeToString(
                                        staffModel.getAvatar()));
                }
                return staffModel;
        }

        @Override
        public void update(Long id, StaffModel model) {
                if (model.getAvatar() != null && model.getAvatar().length == 0) {
                        model.setAvatar(null);
                }
                staffDao.update(id, objectMappingModelToEntity(model));
        }

        @Override
        public void add(StaffModel staffModel) {
                if (staffModel.getAvatar().length == 0) {
                        staffModel.setAvatar(null);
                }
                staffDao.add(objectMappingModelToEntity(staffModel));
        }

        @Override
        public void delete(Long id) {
                staffDao.delete(id);
        }



}
