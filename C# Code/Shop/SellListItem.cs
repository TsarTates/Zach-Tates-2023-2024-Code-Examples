using CSE5912.PenguinProductions.Items;
using TMPro;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

namespace CSE5912.PenguinProductions
{
    public class SellListItem : MonoBehaviour, IPointerEnterHandler, IPointerExitHandler, IPointerClickHandler
    {
        [SerializeField] private ShopUI _shopUI;
        [SerializeField] private Image _itemIcon, _selectionCover;
        [SerializeField] private TextMeshProUGUI _itemName;

        private readonly Color NORMAL_COLOR = new(1, 1, 1, 0);
        private readonly Color HOVERED_COLOR = new(1, 1, 1, 0.01f);
        private readonly Color SELECTED_COLOR = new(1, 1, 1, 0.03f);

        public Item InventoryItem { get; private set; }
        public int InventorySlotIndex { get; private set; }

        private Item _nullItem;

        private void Awake()
        {
            _itemIcon.type = Image.Type.Simple;
            _itemIcon.preserveAspect = true;
            _selectionCover.color = NORMAL_COLOR;
        }

        public void Init(Item inventoryItem, int inventorySlotIndex, Item nullItem) 
        {
            _nullItem = nullItem;
            SetItem(inventoryItem);
            InventorySlotIndex = inventorySlotIndex;
        }

        public void SetItem(Item inventoryItem)
        {
            InventoryItem = inventoryItem;
            _itemIcon.sprite = inventoryItem.icon;
            _itemName.text = inventoryItem.itemName;
            gameObject.SetActive(inventoryItem != _nullItem);
        }

        public void SetSelected(bool selected)
        {
            _selectionCover.color = selected ? SELECTED_COLOR : NORMAL_COLOR;
        }

        public void OnPointerEnter(PointerEventData eventData)
        {
            if (_selectionCover.color != SELECTED_COLOR) _selectionCover.color = HOVERED_COLOR;
        }

        public void OnPointerExit(PointerEventData eventData)
        {
            if (_selectionCover.color != SELECTED_COLOR) _selectionCover.color = NORMAL_COLOR;
        }

        public void OnPointerClick(PointerEventData eventData)
        {
            _shopUI.SetSelectedSellListItem(this);
        }
    }
}
