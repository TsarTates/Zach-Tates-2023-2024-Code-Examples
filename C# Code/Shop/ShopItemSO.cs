using UnityEngine;

namespace CSE5912.PenguinProductions
{
    [CreateAssetMenu(fileName = "shopMenu", menuName = "Scriptable Objects/New Shop Item", order = 1)]
    public class ShopItemSO : ScriptableObject
    {
        public string title;
        public string description;
        public int baseCost;
        public Sprite icon;
        public GameObject item;
    }
}
