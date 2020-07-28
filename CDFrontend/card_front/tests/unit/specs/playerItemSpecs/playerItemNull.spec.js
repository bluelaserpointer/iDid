jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({data: null})),
  post: jest.fn(() => Promise.resolve({data: null}))
}));

import {createLocalVue, mount} from '@vue/test-utils'
import PlayerItemPanel from '@/components/table/playerItemPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerItemPanel.vue', () => {
  const wrapper = mount(PlayerItemPanel,
    {
      localVue
    });

  it('Player Item Panel Nulls created',  async () => {
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/ownItem/getAllOwnItems');
  });

  it('Player Item Panel Nulls confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeFalsy();
  });

  it('Player Item Panel Nulls deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith("http://localhost:8080/ownItem/getAllOwnItems");
  });

  it('Player Item Panel Nulls createData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.createData('temp');
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

  it('Player Item Panel Nulls updateData', async () => {
    wrapper.vm.panelVisible = true;
    await wrapper.vm.updateData('temp');
    expect(wrapper.vm.panelVisible).toBeTruthy();
  });

});