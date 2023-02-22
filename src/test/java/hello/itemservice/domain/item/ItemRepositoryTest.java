package hello.itemservice.domain.item;

// 스프링을 사용하지 않고 test

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemRepositoryTest {

    private ItemRepository repository = new ItemRepository();

   // 각 테스트가 끝날 때마다 실행
    @AfterEach
    void afterEach(){
     repository.clearStore();
    }

    @Test
    void saveTest(){
     // given
     Item item = new Item("itemA", 10000, 10);

     // when
     Item savedItem = repository.save(item);
     // then
     Item foundItem = repository.findById(item.getId());
     Assertions.assertThat(foundItem).isEqualTo(savedItem);
    }

    @Test
    void findAllTest(){
     // given
     Item item1 = new Item("itemA", 10000, 10);
     Item item2 = new Item("itemB", 20000, 20);

     repository.save(item1);
     repository.save(item2);
     // when (findAll 이 목적이기 때문에 when절에서 findAll() 을 실행행
     List<Item> items = repository.findAll();
      // then
     Assertions.assertThat(items).hasSize(2);
     Assertions.assertThat(items).contains(item1, item2);
 }

     @Test
     void updateTest(){
      // given
      Item itemA = new Item("itemA", 10000, 10);
      repository.save(itemA);

      //when
      Item updateParam = new Item("itemB", 20000, 20);
      repository.update(itemA.getId(), updateParam);
      //then
      Item foundItem = repository.findById(itemA.getId());
      Assertions.assertThat(foundItem.getItemName()).isEqualTo(updateParam.getItemName());
      Assertions.assertThat(foundItem.getPrice()).isEqualTo(updateParam.getPrice());
      Assertions.assertThat(foundItem.getQuantity()).isEqualTo(updateParam.getQuantity());
     }



}
