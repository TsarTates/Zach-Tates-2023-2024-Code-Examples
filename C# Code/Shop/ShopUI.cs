using CSE5912.PenguinProductions.Items;
using CSE5912.PenguinProductions.Singleton;
using System.Collections.Generic;
using TMPro;
using Unity.Netcode;
using UnityEngine;
using UnityEngine.InputSystem;
using UnityEngine.UI;

namespace CSE5912.PenguinProductions
{
    public class ShopUI : MonoBehaviour
    {
        [SerializeField] private Button _buy, _sell, _back, _buySell;
        [SerializeField] private TextMeshProUGUI _infoName, _infoDesc, _infoPrice, _noItems, _money;
        [SerializeField] private CanvasGroup _buyList, _sellList, _info;
        [SerializeField] private GameObject _buyItemTemplate, _buyItemParent, _sellItemTemplate, _sellItemParent;
        [SerializeField] private ShopItemSO[] _buyItems;
        [SerializeField] private AudioClip _buySound;

        private TerrifyingTerry _terry;
        private readonly List<BuyListItem> _buyListItems = new();
        private BuyListItem _selectedBuyListItem;
        private readonly List<SellListItem> _sellListItems = new();
        private SellListItem _selectedSellListItem;

        #region Inventory-Related Stuff (almost a copy of InventoryUI)

        [SerializeField] private InventoryController _controller;
        [SerializeField] private ShopSlot[] _slots;
        [SerializeField] private Image _itemMovedIcon;
        [SerializeField] private OutsideInventory _outsideArea;

        private GameObject _nullItemObject;
        private Item _nullItem;
        private GameObject _itemObjectBeingMoved;

        private void OnDisable()
        {
            // If you exit the shop while having an item still picked up, just drop it
            if (_itemObjectBeingMoved != _nullItemObject)
            {
                _controller.DropItemFromInventory(_itemObjectBeingMoved);
                SetItemObjectBeingMoved(_nullItemObject);
            }

            if (_buyListItems.Count > 0) 
            {
                SetSelectedBuyListItem(_buyListItems[0]);
                SetSelectedSellListItem(_sellListItems[0]);
                SetTab(true);
            }
        }

        private void Update()
        {
            _itemMovedIcon.gameObject.GetComponent<RectTransform>().position = Mouse.current.position.value;
        }

        public void Init(GameObject nullItemObject)
        {
            _nullItemObject = nullItemObject;
            _nullItem = nullItemObject.GetComponent<ItemController>().item;

            foreach (ShopSlot s in _slots) s.Init(_nullItem);
            SetItemObjectBeingMoved(nullItemObject);

            _itemMovedIcon.type = Image.Type.Simple;
            _itemMovedIcon.preserveAspect = true;
            _itemMovedIcon.gameObject.SetActive(false);
        }

        private void SetItemObjectBeingMoved(GameObject itemObject)
        {
            _itemObjectBeingMoved = itemObject;
            _itemMovedIcon.sprite = itemObject.GetComponent<ItemController>().item.icon;
            _itemMovedIcon.gameObject.SetActive(itemObject.GetComponent<ItemController>().item != _nullItem);
        }

        public void SetSlotItem(Item item, int slotIndex)
        {
            _slots[slotIndex].SetItem(item);
            if (_sellListItems.Count > 0) 
            {
                _sellListItems[slotIndex].SetItem(item);
                UpdateShopUI();
            }
        }

        public void HandleClick()
        {
            // If one of the slots was clicked, pick up that item
            for (int i = 0; i < _slots.Length; i++)
            {
                if (_slots[i].WasClicked())
                {
                    if (i == InventoryController.WEARABLE_SLOT_INDEX && !_controller.IsItemType(_itemObjectBeingMoved, ItemType.Wearable) && _itemObjectBeingMoved != _nullItemObject)
                        return;
                    if (i == InventoryController.PET_SLOT_INDEX && !_controller.IsItemType(_itemObjectBeingMoved, ItemType.Pet) && _itemObjectBeingMoved != _nullItemObject)
                        return;

                    GameObject previousItemObject = _controller.SetItem(_itemObjectBeingMoved, i);
                    SetItemObjectBeingMoved(previousItemObject);
                    return;
                }
            }

            // If the area outside the inventory was clicked, drop whatever item is currently picked up
            if (_outsideArea.WasClicked())
            {
                _controller.DropItemFromInventory(_itemObjectBeingMoved);
                SetItemObjectBeingMoved(_nullItemObject);
            }
        }

        #endregion

        private void Awake()
        {
            _buy.onClick.AddListener(ChangeToBuyTab);
            _sell.onClick.AddListener(ChangeToSellTab);
            _back.onClick.AddListener(CloseShop);
            _buySell.onClick.AddListener(BuySell);

            foreach (ShopItemSO s in _buyItems) 
            {
                BuyListItem b = Instantiate(_buyItemTemplate, _buyItemParent.transform).GetComponent<BuyListItem>();
                b.Init(s);
                b.gameObject.SetActive(true);
                _buyListItems.Add(b);
            }
            SetSelectedBuyListItem(_buyListItems[0]);

            for (int i = 0; i < _controller.ItemObjects.Length; i++) 
            {
                SellListItem s = Instantiate(_sellItemTemplate, _sellItemParent.transform).GetComponent<SellListItem>();
                s.Init(_controller.ItemObjects[i].GetComponent<ItemController>().item, i, _nullItem);
                _sellListItems.Add(s);
            }
            SetSelectedSellListItem(_sellListItems[0]);

            SetTab(true);
        }

        private void OnEnable()
        {
            UpdateMoneyUI();
        }

        /// <summary>
        /// Links Terry to the shop UI.
        /// </summary>
        /// <param name="terry">Terry to link to the UI.</param>
        public void SetTerry(TerrifyingTerry terry)
        {
            _terry = terry;
        }

        #region Input / Button Handling

        public void OpenShop()
        {
            InputManager.Singleton.SetLoadout(InputManager.ControlsLoadout.SHOP);
            UIManager.Singleton.SetUIMode(UIManager.UIMode.SHOP);
        }

        public void CloseShop()
        {
            if (_terry != null) _terry.PlayGoodbye();
            InputManager.Singleton.SetLoadout(InputManager.ControlsLoadout.PLAYING);
            UIManager.Singleton.SetUIMode(UIManager.UIMode.HUD);
        }

        private void SetTab(bool isBuyTab)
        {
            _buy.interactable = !isBuyTab;
            _buyList.gameObject.SetActive(isBuyTab);
            _sell.interactable = isBuyTab;
            _sellList.gameObject.SetActive(!isBuyTab);
            _buySell.GetComponentInChildren<TextMeshProUGUI>().text = isBuyTab ? "Purchase" : "Sell";
            UpdateShopUI();
        }

        private void ChangeToBuyTab() => SetTab(true);

        private void ChangeToSellTab() => SetTab(false);

        private void BuySell()
        {
            if (!_buy.interactable) Buy();
            else Sell();
        }

        #endregion

        #region Updating Player's Selection / UI

        public void SetSelectedBuyListItem(BuyListItem b)
        {
            foreach (BuyListItem bu in _buyListItems) bu.SetSelected(bu == b);

            _selectedBuyListItem = b;
            _infoName.text = b.ShopItem.title;
            _infoDesc.text = b.ShopItem.description;
            _infoPrice.text = "$" + b.ShopItem.baseCost;
        }

        public void SetSelectedSellListItem(SellListItem s)
        {
            foreach (SellListItem se in _sellListItems) se.SetSelected(se == s);

            _selectedSellListItem = s;
            _infoName.text = s.InventoryItem.itemName;
            _infoDesc.text = s.InventoryItem.description;
            _infoPrice.text = "$" + s.InventoryItem.sellPrice;
        }

        private void UpdateShopUI()
        {
            if (!_buy.interactable)
            {
                _infoName.text = _selectedBuyListItem.ShopItem.title;
                _infoDesc.text = _selectedBuyListItem.ShopItem.description;
                _infoPrice.text = "$" + _selectedBuyListItem.ShopItem.baseCost;
                _buySell.interactable = true;
            }
            else
            {
                SellListItem selectedSellListItem = _selectedSellListItem;
                if (!selectedSellListItem.gameObject.activeSelf)
                {
                    foreach (SellListItem s in _sellListItems)
                    {
                        if (s.gameObject.activeSelf)
                        {
                            selectedSellListItem = s;
                            break;
                        }
                    }
                }

                if (!selectedSellListItem.gameObject.activeSelf)
                {
                    _infoName.text = "???";
                    _infoDesc.text = "???";
                    _infoPrice.text = "$???";
                    _buySell.interactable = false;
                    _noItems.gameObject.SetActive(true);
                }
                else
                {
                    SetSelectedSellListItem(selectedSellListItem);
                    _buySell.interactable = true;
                    _noItems.gameObject.SetActive(false);
                }
            }
        }

        private void UpdateMoneyUI()
        {
            _money.text = "$" + GameManager.Singleton.LocalPlayer.GetComponent<PlayerStatsSystem>().PlayerMoney;
        }

        #endregion

        #region Buying / Selling

        private void Buy() 
        {
            // Check if player has enough money
            if (GameManager.Singleton.LocalPlayer.GetComponent<PlayerStatsSystem>().PlayerMoney < _selectedBuyListItem.ShopItem.baseCost)
            {
                MessageBox.Singleton.SendNewMessage("Not enough money!", Color.red);
                return;
            }

            // Check if player has enough inventory space
            int slotIndex = _controller.FindEmptySlot(out bool slotFound);
            if (!slotFound)
            {
                MessageBox.Singleton.SendNewMessage("Inventory full!", Color.red);
                return;
            }

            // Purchase the item, spawn it, and add it to the player's inventory
            GameManager.Singleton.LocalPlayer.GetComponent<PlayerStatsSystem>().RemoveMoney(_selectedBuyListItem.ShopItem.baseCost);
            UpdateMoneyUI();
            AudioManager.Singleton.PersistentPlayOneShot(_buySound, AudioManager.AudioGroup.UI);
            if (NetworkManager.Singleton.IsHost)
            {
                GameObject itemObject = ItemManager.Singleton.SpawnItem(_selectedBuyListItem.ShopItem.item, new Vector3(0, -10, 0));
                if (itemObject != null)
                {
                    ItemManager.Singleton.SetParent(itemObject, null);
                    ItemManager.Singleton.SetActive(itemObject, false);
                    _controller.SetItem(itemObject, slotIndex);
                }
            }
            else ItemManager.Singleton.SpawnInventoryItemAsClientServerRpc(NetworkManager.Singleton.LocalClientId, _selectedBuyListItem.ShopItem.title, slotIndex);
        }

        private void Sell() 
        {
            GameManager.Singleton.LocalPlayer.GetComponent<PlayerStatsSystem>().AddMoney(_selectedSellListItem.InventoryItem.sellPrice);
            UpdateMoneyUI();
            GameObject itemObject = null;
            for (int i = 0; i < _sellListItems.Count; i++)
            {
                if (_sellListItems[i] == _selectedSellListItem) 
                {
                    itemObject = _controller.RemoveItem(i);
                    break;
                }
            }

            if (NetworkManager.Singleton.IsHost) itemObject.GetComponent<NetworkObject>().Despawn();
            else ItemManager.Singleton.DespawnItemAsClientServerRpc(itemObject);
        }

        #endregion
    }
}
